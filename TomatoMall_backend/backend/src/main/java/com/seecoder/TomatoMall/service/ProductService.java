package com.seecoder.TomatoMall.service;

import java.util.List;

import com.seecoder.TomatoMall.po.Stockpile;
import com.seecoder.TomatoMall.vo.ProductVO;

public interface ProductService {
    List<ProductVO> getAll();
    ProductVO getById(int id);
    Boolean updateProduct(ProductVO productVO);
    ProductVO addProduct(ProductVO productVO);
    Boolean deleteProduct(String id);
    Boolean changeStockpile(String productid, int amount);
    Stockpile getStockpile(String productid);
    String getCover(String productid);
} 
