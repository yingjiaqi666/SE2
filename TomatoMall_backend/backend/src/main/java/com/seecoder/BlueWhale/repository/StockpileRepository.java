package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.po.Stockpile;

import org.springframework.data.jpa.repository.JpaRepository;
public interface StockpileRepository extends JpaRepository<Stockpile,Integer>{
    Stockpile findByProductid(String productid);
}
