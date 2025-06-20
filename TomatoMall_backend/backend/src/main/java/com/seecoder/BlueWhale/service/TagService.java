package com.seecoder.BlueWhale.service;

import java.util.List;

import com.seecoder.BlueWhale.vo.TagVO;

public interface TagService {
    List<TagVO> getAll();
    TagVO getById(int id);
    TagVO addTag(TagVO tagVO);
    Boolean deleteTag(String id);
    Boolean deleteBook(String tagId ,String bookId);
    Boolean addBook(String tagId ,String bookId);
    List<String>getBooks(String tagId);
    List<Integer>getTagsOfBook(String productId);

} 
