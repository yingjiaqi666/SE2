package com.seecoder.BlueWhale.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seecoder.BlueWhale.po.Advertisement;

public interface AdvertisementRepository  extends JpaRepository<Advertisement,Integer>{
    Advertisement findById(int id);
    
} 