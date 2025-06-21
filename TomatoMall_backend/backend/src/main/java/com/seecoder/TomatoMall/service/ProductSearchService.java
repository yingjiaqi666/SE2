package com.seecoder.TomatoMall.service;

import com.seecoder.TomatoMall.vo.ProductSearchDTO;
import com.seecoder.TomatoMall.vo.ProductSimilarityVO;
import com.seecoder.TomatoMall.vo.ProductVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductSearchService {

    /**
     * 模糊搜索商品（带相似度评分）
     * @param searchDTO 包含关键词、相似度阈值、分页参数
     * @return 按相似度降序排列的商品列表
     */
    List<ProductSimilarityVO> fuzzySearchWithSimilarity(ProductSearchDTO searchDTO);
}