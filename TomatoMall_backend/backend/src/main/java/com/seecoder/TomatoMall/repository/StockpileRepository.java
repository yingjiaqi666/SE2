package com.seecoder.TomatoMall.repository;

import com.seecoder.TomatoMall.po.Stockpile;

import org.springframework.data.jpa.repository.JpaRepository;
public interface StockpileRepository extends JpaRepository<Stockpile,Integer>{
    Stockpile findByProductid(String productid);
}
