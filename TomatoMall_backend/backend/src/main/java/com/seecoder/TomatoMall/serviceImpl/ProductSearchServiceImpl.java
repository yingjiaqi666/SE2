package com.seecoder.TomatoMall.serviceImpl;

import com.seecoder.TomatoMall.exception.TomatoMallException;
import com.seecoder.TomatoMall.po.Product;
import com.seecoder.TomatoMall.po.Specification;
import com.seecoder.TomatoMall.repository.ProductRepository;
import com.seecoder.TomatoMall.service.ProductSearchService;
import com.seecoder.TomatoMall.util.PinyinUtil;
import com.seecoder.TomatoMall.util.SimilarityUtil;
import com.seecoder.TomatoMall.vo.ProductSearchDTO;
import com.seecoder.TomatoMall.vo.ProductSimilarityVO;
import com.seecoder.TomatoMall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    @Autowired
    private ProductRepository productRepo;

    @Override
    public List<ProductSimilarityVO> fuzzySearchWithSimilarity(ProductSearchDTO searchDTO) {
        long startTime = System.currentTimeMillis();
        List<Product> allProducts = productRepo.findAll();

        List<ProductSimilarityVO> results = allProducts.stream()
                .parallel()
                .map(product -> {
                    String keyword = searchDTO.getKeyword().toLowerCase();
                    String title = product.getTitle().toLowerCase();

                    // 1. 原始匹配检查
                    boolean containsOriginal = title.contains(keyword);

                    // 2. 拼音处理
                    String titlePinyin = PinyinUtil.getFullPinyin(title);
                    String titleInitials = PinyinUtil.getInitialPinyin(title);
                    String keywordPinyin = PinyinUtil.getFullPinyin(keyword);
                    String keywordInitials = PinyinUtil.getInitialPinyin(keyword);

                    // 3. 拼音匹配检查
                    boolean containsFullPinyin = titlePinyin.contains(keywordPinyin);
                    boolean containsInitials = titleInitials.contains(keywordInitials);
                    boolean pinyinMatch = !keyword.matches("[\\u4E00-\\u9FA5]") &&
                            (containsFullPinyin || containsInitials);

                    // 4. 综合相似度计算（保留原有算法）
                    double similarity = calculateCombinedSimilarity(
                            keyword, title,
                            titlePinyin, titleInitials
                    );

                    // 5. 强制包含逻辑：如果包含关键词，则提升相似度
                    if (containsOriginal || pinyinMatch) {
                        similarity = Math.max(similarity, 1.0); // 确保至少为1.0
                    }

                    return new ProductSimilarityVO(
                            product.toVO(),
                            similarity,
                            containsOriginal || pinyinMatch
                    );
                })
                // 保留原有过滤条件，但包含匹配的结果一定会通过
                .filter(vo -> vo.getSimilarity() >= searchDTO.getMinSimilarity() || vo.isMatch())
                .sorted(Comparator.comparing(ProductSimilarityVO::getSimilarity).reversed())
                .skip((searchDTO.getPage() - 1) * searchDTO.getPageSize())
                .limit(searchDTO.getPageSize())
                .collect(Collectors.toList());

        return results;
    }

    // 保留原有相似度计算方法
    private double calculateCombinedSimilarity(
            String keyword, String title,
            String titlePinyin, String titleInitials
    ) {
        // 原始文本相似度
        double originalSimilarity = SimilarityUtil.levenshteinRatio(keyword, title);

        // 拼音相似度（仅当中文匹配度低时计算）
        double pinyinSimilarity = originalSimilarity < 0.7 ?
                Math.max(
                        SimilarityUtil.levenshteinRatio(
                                PinyinUtil.getFullPinyin(keyword),
                                titlePinyin
                        ),
                        SimilarityUtil.levenshteinRatio(
                                PinyinUtil.getInitialPinyin(keyword),
                                titleInitials
                        )
                ) : 0;

        // 取最高相似度
        return Math.max(originalSimilarity, pinyinSimilarity);
    }
}