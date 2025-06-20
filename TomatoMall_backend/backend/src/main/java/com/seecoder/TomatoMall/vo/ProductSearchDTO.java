package com.seecoder.TomatoMall.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchDTO {
    private String keyword;          // 搜索关键词
    private Double minSimilarity = 0.6; // 相似度阈值（默认0.6）
    private Integer page = 1;
    private Integer pageSize = 10;
}