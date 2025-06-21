package com.seecoder.TomatoMall.controller;
import com.seecoder.TomatoMall.service.CommentService;
import com.seecoder.TomatoMall.vo.CommentVO;
import com.seecoder.TomatoMall.vo.ResultVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResultVO<List<CommentVO>> getAll(){
        return ResultVO.buildSuccess(commentService.getAll());
    }

    @GetMapping("/{id}")
    public ResultVO<CommentVO> getById(@PathVariable String id){
        int idInt = Integer.parseInt(id);
        return ResultVO.buildSuccess(commentService.getById(idInt));
    }

    @PostMapping
    public ResultVO<CommentVO> addComment(@RequestBody CommentVO commentVO){
        return ResultVO.buildSuccess(commentService.addComment(commentVO));
    }

    @DeleteMapping("/{id}")
    public ResultVO<String> deleteComment(@PathVariable Integer Id){
        if(commentService.deleteComment(Id.toString())){
            return ResultVO.buildSuccess("添加成功");
        }else{
            return ResultVO.buildFailure("书籍不存在");
        }
    }

    //search

    //TODO

    @GetMapping("/book/{id}")
    public ResultVO<List<CommentVO>> searchByBook(@PathVariable Integer Id){
        return ResultVO.buildSuccess(commentService.searchByBook(Id.toString()));
    }
}
