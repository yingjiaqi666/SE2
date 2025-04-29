package com.seecoder.TomatoMall.repository;

import com.seecoder.TomatoMall.po.Product;

import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product, Integer>{
    Product findById(int id);
}