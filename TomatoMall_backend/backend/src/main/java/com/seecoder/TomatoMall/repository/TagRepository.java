package com.seecoder.TomatoMall.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seecoder.TomatoMall.po.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{
    Tag findById(int id);
    Tag findByName(String name);
}