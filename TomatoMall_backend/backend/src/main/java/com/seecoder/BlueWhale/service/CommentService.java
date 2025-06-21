package com.seecoder.BlueWhale.service;

import java.util.List;

import com.seecoder.BlueWhale.po.Comment;
import com.seecoder.BlueWhale.vo.CommentVO;

public interface CommentService {
    List<CommentVO> getAll();
    CommentVO getById(int id);
    CommentVO addComment(Comment comment);
    Boolean deleteComment(String id);
    List<CommentVO> searchByBook(String bookId);
}