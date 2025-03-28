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

    @GetMapping
    public ResultVO<ProductVO> getById(@RequestParam("id") String id){
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

    @DeleteMapping
    public ResultVO<Boolean> deleteProduct(@RequestParam("id") String id){
        return ResultVO.buildSuccess(productService.deleteProduct(id));
    }

    @PatchMapping("/stockpile")
    public ResultVO<Boolean> changeStockpile(@RequestParam("productid") String productid ,@RequestBody int amount){
        return ResultVO.buildSuccess(productService.changeStockpile(productid, amount));
    }

    @GetMapping("/stockpile")
    public ResultVO<Stockpile> getStockpile(@RequestParam("productid") String productid){
        return ResultVO.buildSuccess(productService.getStockpile(productid));//如果productid错误则返回null
    }

}
