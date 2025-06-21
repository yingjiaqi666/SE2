package com.seecoder.BlueWhale.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.Comment;
import com.seecoder.BlueWhale.repository.CommentRepository;
import com.seecoder.BlueWhale.service.CommentService;
import com.seecoder.BlueWhale.vo.CommentVO;

@Service
public class CommentServiceImp implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<CommentVO> getAll() {
        return commentRepository.findByFatherId("").stream().map(Comment::toVO).collect(Collectors.toList());
    }

    @Override
    public CommentVO getById(int id) {
        Comment comment = commentRepository.findById(id);
        if(comment==null){
            throw BlueWhaleException.commentNotFound();
        }
        return commentRepository.findById(id).toVO();
    }

    @Override
    public CommentVO addComment(Comment comment) {
        if(comment.getFatherId()!=null){
            Comment father = commentRepository.findById(Integer.parseInt(comment.getFatherId()));
            if(father == null){
                throw BlueWhaleException.commentNotFound();
            }
        }
        commentRepository.save(comment);
        return comment.toVO();
    }

    @Override
    public Boolean deleteComment(String id) {
        Comment comment = commentRepository.findById(Integer.parseInt(id));
        if(comment==null){
            throw BlueWhaleException.commentNotFound();
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
