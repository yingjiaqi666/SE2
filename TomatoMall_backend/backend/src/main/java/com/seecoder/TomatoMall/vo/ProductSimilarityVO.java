package com.seecoder.TomatoMall.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSimilarityVO {
    // getter 和 setter 方法
    @Setter
    @Getter
    private ProductVO product;
    @Getter
    private double similarity;

    // 新增拼音匹配相关字段
    private boolean containsOriginal;    // 是否包含原始关键词
    private boolean containsFullPinyin;  // 是否包含全拼
    private boolean containsInitials;    // 是否包含首字母
    private boolean pinyinMatch;          // 是否为拼音匹配

    // 构造方法
    public ProductSimilarityVO(ProductVO product, double similarity,
                               boolean containsOriginal, boolean containsFullPinyin,
                               boolean containsInitials, boolean pinyinMatch) {
        this.product = product;
        this.similarity = similarity;
        this.containsOriginal = containsOriginal;
        this.containsFullPinyin = containsFullPinyin;
        this.containsInitials = containsInitials;
        this.pinyinMatch = pinyinMatch;
    }

    // 简化构造方法（兼容旧代码）
    public ProductSimilarityVO(ProductVO product, double similarity, boolean isMatch) {
        this(product, similarity, isMatch, false, false, false);
    }

    public boolean isMatch() {
        return  containsOriginal||containsFullPinyin||containsInitials||pinyinMatch;
    }
}