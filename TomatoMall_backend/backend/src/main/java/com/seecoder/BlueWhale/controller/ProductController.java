package com.seecoder.BlueWhale.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.seecoder.BlueWhale.service.ProductService;
import com.seecoder.BlueWhale.vo.ProductVO;
import com.seecoder.BlueWhale.vo.ResultVO;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResultVO<List<ProductVO>> getAll(){
        return ResultVO.buildSuccess(productService.getAll());
    }

    @GetMapping
    public ResultVO<ProductVO> getById(@RequestParam("id") String id){
        int idInt = Integer.parseInt(id);
        return ResultVO.buildSuccess(productService.getById(idInt));
    }

    @PutMapping
    public ResultVO<Boolean> updateProduct(@RequestBody ProductVO productVO){
        return ResultVO.buildSuccess(productService.updateProduct(productVO));
    }

}
