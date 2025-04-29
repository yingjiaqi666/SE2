package com.seecoder.TomatoMall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seecoder.TomatoMall.po.Advertisement;

public interface AdvertisementRepository  extends JpaRepository<Advertisement,Integer>{
    Advertisement findById(int id);
    
} 