package com.seecoder.BlueWhale.service;

import java.util.List;

import com.seecoder.BlueWhale.vo.CommentVO;

public interface CommentService {
    List<CommentVO> getAll();
    CommentVO getById(int id);
    CommentVO addComment(CommentVO commentVO);
    Boolean deleteComment(String id);
    List<CommentVO> searchByBook(String bookId);
}