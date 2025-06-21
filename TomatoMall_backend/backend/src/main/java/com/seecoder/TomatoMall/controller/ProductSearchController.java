package com.seecoder.TomatoMall.controller;

import com.seecoder.TomatoMall.service.ProductSearchService;
import com.seecoder.TomatoMall.vo.ProductSearchDTO;
import com.seecoder.TomatoMall.vo.ProductSimilarityVO;
import com.seecoder.TomatoMall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 在Controller中使用
@RestController
@RequestMapping("/api/search")
public class ProductSearchController {

    @Autowired
    private ProductSearchService searchService;

    @PostMapping
    public ResultVO<List<ProductSimilarityVO>> search(@RequestBody ProductSearchDTO dto) {
        return ResultVO.buildSuccess(searchService.fuzzySearchWithSimilarity(dto));
    }
}