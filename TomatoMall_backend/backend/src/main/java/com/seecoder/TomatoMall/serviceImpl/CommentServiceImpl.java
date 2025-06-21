package com.seecoder.TomatoMall.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seecoder.TomatoMall.exception.TomatoMallException;
import com.seecoder.TomatoMall.po.Comment;
import com.seecoder.TomatoMall.repository.CommentRepository;
import com.seecoder.TomatoMall.service.CommentService;
import com.seecoder.TomatoMall.vo.CommentVO;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<CommentVO> getAll() {
        return commentRepository.findAllFatherIdIsNull().stream().map(Comment::toVO).collect(Collectors.toList());
    }

    @Override
    public CommentVO getById(int id) {
        Comment comment = commentRepository.findById(id);
        if(comment==null){
            throw TomatoMallException.commentNotFound();
        }
        return commentRepository.findById(id).toVO();
    }

    @Override
    public CommentVO addComment(CommentVO commentVO) {
        if(commentVO.getFatherId()!=null){
            Comment comment = commentRepository.findById(Integer.parseInt(commentVO.getFatherId()));
            if(comment == null){
                throw TomatoMallException.commentNotFound();
            }
        }
        commentRepository.save(commentVO.toPO());
        return commentVO;
    }

    @Override
    public Boolean deleteComment(String id) {
        Comment comment = commentRepository.findById(Integer.parseInt(id));
        if(comment==null){
            throw TomatoMallException.commentNotFound();
        }
        commentRepository.delete(comment);
        List<Comment> comments= commentRepository.findByFatherId(id);
        for(Comment cm : comments){
            commentRepository.delete(cm);
        }
        return true;
    }

    @Override
    public List<CommentVO> searchByBook(String bookId) {
        return commentRepository.findByBookId(Integer.parseInt(bookId)).stream().map(Comment::toVO).collect(Collectors.toList());
    }
    
}
