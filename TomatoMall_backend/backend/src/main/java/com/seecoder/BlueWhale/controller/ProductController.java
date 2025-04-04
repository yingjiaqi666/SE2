package com.seecoder.BlueWhale.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.seecoder.BlueWhale.po.Stockpile;
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

    @GetMapping("/{id}")
    public ResultVO<ProductVO> getById(@PathVariable String id){
        int idInt = Integer.parseInt(id);
        return ResultVO.buildSuccess(productService.getById(idInt));
    }

    @PutMapping
    public ResultVO<Boolean> updateProduct(@RequestBody ProductVO productVO){
        return ResultVO.buildSuccess(productService.updateProduct(productVO));
    }

    @PostMapping
    public ResultVO<ProductVO> addProduct(@RequestBody ProductVO productVO){
        return ResultVO.buildSuccess(productService.addProduct(productVO));
    }

    @DeleteMapping("/{id}")
    public ResultVO<Boolean> deleteProduct(@PathVariable String id){
        return ResultVO.buildSuccess(productService.deleteProduct(id));
    }

    @PatchMapping("/stockpile/{productId}")
    public ResultVO<Boolean> changeStockpile(@PathVariable String productId ,@RequestBody int amount){
        return ResultVO.buildSuccess(productService.changeStockpile(productId, amount));
    }

    @GetMapping("/stockpile/{productId}")
    public ResultVO<Stockpile> getStockpile(@PathVariable String productId){
        return ResultVO.buildSuccess(productService.getStockpile(productId));//如果productid错误则返回null
    }

}
