package com.seecoder.BlueWhale.service;

import java.util.List;

import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.vo.ProductVO;

public interface ProductService {
    List<ProductVO> getAll();
    ProductVO getById(int id);
    Boolean updateProduct(ProductVO productVO);
} 
