package com.seecoder.TomatoMall.serviceImpl;

import com.seecoder.TomatoMall.exception.TomatoMallException;
import com.seecoder.TomatoMall.po.Product;
import com.seecoder.TomatoMall.po.Specification;
import com.seecoder.TomatoMall.repository.ProductRepository;
import com.seecoder.TomatoMall.service.ProductSearchService;
import com.seecoder.TomatoMall.util.SimilarityUtil;
import com.seecoder.TomatoMall.vo.ProductSearchDTO;
import com.seecoder.TomatoMall.vo.ProductSimilarityVO;
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

        // 1. 获取所有商品（生产环境应加预筛选条件）
        List<Product> allProducts = productRepo.findAll();

        // 2. 计算相似度并排序
        List<ProductSimilarityVO> result = allProducts.stream()
                .parallel() // 并行流加速计算
                .map(product -> {
                    double similarity = SimilarityUtil.levenshteinRatio(
                            searchDTO.getKeyword().toLowerCase(),
                            product.getTitle().toLowerCase()
                    );
                    return new ProductSimilarityVO(product.toVO(), similarity);
                })
                .filter(vo -> vo.getSimilarity() >= searchDTO.getMinSimilarity())
                .sorted(Comparator.comparing(ProductSimilarityVO::getSimilarity).reversed())
                .skip((searchDTO.getPage() - 1) * searchDTO.getPageSize())
                .limit(searchDTO.getPageSize())
                .collect(Collectors.toList());

        return result;
    }
}