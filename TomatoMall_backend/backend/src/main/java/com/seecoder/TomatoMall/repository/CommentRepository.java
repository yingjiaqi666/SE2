package com.seecoder.TomatoMall.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seecoder.TomatoMall.po.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
    List<Comment> findByFatherId(String fatherId);
    List<Comment> findByFatherIdIsNull();
    Comment findById(int id);
    List<Comment> findByBookId(int bookId);
}
