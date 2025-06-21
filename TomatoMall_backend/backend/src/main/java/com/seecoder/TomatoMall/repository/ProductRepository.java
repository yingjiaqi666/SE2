package com.seecoder.TomatoMall.repository;

import com.seecoder.TomatoMall.po.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    Product findById(int id);
    List<Product> findByTitleContainingIgnoreCase(String keyword);
}