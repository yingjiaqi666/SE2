package com.seecoder.BlueWhale.controller;
import java.util.List;

import com.seecoder.BlueWhale.vo.TagVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.seecoder.BlueWhale.service.TagService;
import com.seecoder.BlueWhale.vo.ResultVO;

@RestController
@RequestMapping("/api")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public ResultVO<List<TagVO>> getAll(){
        return ResultVO.buildSuccess(tagService.getAll());
    }

    @GetMapping("/tag/{id}")
    public ResultVO<TagVO> getById(@PathVariable String id){
        int idInt = Integer.parseInt(id);
        return ResultVO.buildSuccess(tagService.getById(idInt));
    }

    @DeleteMapping("/tag/{tagid}")
    public ResultVO<String> deleteBook(@PathVariable Integer tagid,@RequestBody Integer bookId){
        if(tagService.deleteBook(tagid.toString(),bookId.toString())){
            return ResultVO.buildSuccess("删除成功");
        }else{
            ResultVO.buildFailure("书籍不存在");
        }
            
        return null;
    }

    @PatchMapping("/tag/{tagid}")
    public ResultVO<String> addBook(@PathVariable Integer tagId,@RequestBody Integer bookId){
        if(tagService.addBook(tagId.toString(),bookId.toString())){
            return ResultVO.buildSuccess("添加成功");
        }else{
            ResultVO.buildFailure("书籍不存在");
        }
        return null;
    }

    @PostMapping("/tag")
    public ResultVO<TagVO> addTag(@RequestBody TagVO tag){
        return ResultVO.buildSuccess(tagService.addTag(tag));
    }

    @DeleteMapping("/tag/delete/{tagid}")
    public ResultVO<String> deleteTag(@PathVariable String tagId ){
        if(tagService.deleteTag(tagId))
            return ResultVO.buildSuccess("删除tag成功");
        return ResultVO.buildFailure("tag不存在");
    }

    @GetMapping("/tag/books/{tagid}")
    public ResultVO<List<String>> getBooks(@PathVariable String tagId){
        return ResultVO.buildSuccess(tagService.getBooks(tagId));
    }

    @GetMapping("/tag/product/{productId}")
    public ResultVO<List<Integer>> getTagsOfBook(@PathVariable String productId){
        return ResultVO.buildSuccess(tagService.getTagsOfBook(productId));//如果Tagid错误则返回null
    }



}
