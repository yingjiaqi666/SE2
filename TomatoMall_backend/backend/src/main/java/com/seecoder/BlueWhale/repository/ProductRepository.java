package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.po.Product;

import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product, Integer>{
    Product findById(int id);
}