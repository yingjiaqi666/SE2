package com.seecoder.TomatoMall.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSimilarityVO {
    private ProductVO product;
    private Double similarity; // 相似度分数（0~1）

    public ProductSimilarityVO(ProductVO product, Double similarity) {
        this.product = product;
        this.similarity = similarity;
    }
}