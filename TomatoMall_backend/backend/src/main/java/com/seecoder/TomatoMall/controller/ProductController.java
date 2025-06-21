package com.seecoder.TomatoMall.controller;
import java.util.List;

import com.seecoder.TomatoMall.vo.StockpileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.seecoder.TomatoMall.po.Stockpile;
import com.seecoder.TomatoMall.service.ProductService;
import com.seecoder.TomatoMall.vo.ProductVO;
import com.seecoder.TomatoMall.vo.ResultVO;

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
    public ResultVO<String> updateProduct(@RequestBody ProductVO productVO){
        if(productService.updateProduct(productVO))
            return ResultVO.buildSuccess("更新成功");
        return null;
    }

    @PostMapping
    public ResultVO<ProductVO> addProduct(@RequestBody ProductVO productVO){
        return ResultVO.buildSuccess(productService.addProduct(productVO));
    }

    @DeleteMapping("/{id}")
    public ResultVO<String> deleteProduct(@PathVariable String id){
        if(productService.deleteProduct(id))
            return ResultVO.buildSuccess("删除成功");
        return null;
    }

    @PatchMapping("/stockpile/{productId}")
    public ResultVO<String> changeStockpile(@PathVariable String productId ,@RequestBody StockpileVO amount){
        if(productService.changeStockpile(productId, amount.getAmount()))
            return ResultVO.buildSuccess("调整库存成功");
        return null;
    }

    @GetMapping("/stockpile/{productId}")
    public ResultVO<Stockpile> getStockpile(@PathVariable String productId){
        return ResultVO.buildSuccess(productService.getStockpile(productId));//如果productid错误则返回null
    }

}
