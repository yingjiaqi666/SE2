package com.seecoder.BlueWhale.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seecoder.BlueWhale.po.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
    List<Comment> findByFatherId(String fatherId);
    Comment findById(int id);
    List<Comment> findByBookId(int bookId);
}
