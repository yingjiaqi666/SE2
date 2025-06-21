package com.seecoder.TomatoMall.controller;
import java.util.*;

import com.seecoder.TomatoMall.vo.TagVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.seecoder.TomatoMall.service.TagService;
import com.seecoder.TomatoMall.vo.ResultVO;

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

    @DeleteMapping("/tag/{tagId}")
    public ResultVO<String> deleteBook(@PathVariable Integer tagId,@RequestBody Integer bookId){
        if(tagService.deleteBook(tagId.toString(),bookId.toString())){
            return ResultVO.buildSuccess("删除成功");
        }else{
            ResultVO.buildFailure("书籍不存在");
        }
            
        return null;
    }

    @PatchMapping("/tag/{tagId}")
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

    @DeleteMapping("/tag/delete/{tagId}")
    public ResultVO<String> deleteTag(@PathVariable String tagId ){
        System.out.println(tagId);
        if(tagService.deleteTag(tagId))
            return ResultVO.buildSuccess("删除tag成功");
        return ResultVO.buildFailure("tag不存在");
    }

    @GetMapping("/tag/books")
    public ResultVO<List<String>> getBooksByTags(@RequestParam List<String> tagIds) {
        Map<String, Integer> bookMatchCount = new HashMap<>();
        for (String tagId : tagIds) {
            List<String> booksForTag = tagService.getBooks(tagId);
            for (String bookId : booksForTag) {
                bookMatchCount.put(bookId, bookMatchCount.getOrDefault(bookId, 0) + 1);
            }
        }
        List<String> sortedBookIds = new ArrayList<>(bookMatchCount.keySet());
        sortedBookIds.sort((a, b) -> {
            int countA = bookMatchCount.get(a);
            int countB = bookMatchCount.get(b);
            return Integer.compare(countB, countA);
        });
        return ResultVO.buildSuccess(sortedBookIds);
    }
    @GetMapping("/tag/product/{productId}")
    public ResultVO<List<Integer>> getTagsOfBook(@PathVariable String productId){
        return ResultVO.buildSuccess(tagService.getTagsOfBook(productId));//如果Tagid错误则返回null
    }

}
