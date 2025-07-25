package com.seecoder.BlueWhale.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.seecoder.BlueWhale.exception.BlueWhaleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seecoder.BlueWhale.po.Tag;
import com.seecoder.BlueWhale.repository.StockpileRepository;
import com.seecoder.BlueWhale.repository.TagRepository;
import com.seecoder.BlueWhale.service.TagService;
import com.seecoder.BlueWhale.vo.TagVO;

@Service
public class TagServiceImp implements TagService{
    @Autowired
    TagRepository tagRepository;

    @Autowired
    StockpileRepository stockpileRepository;

    @Override
    public List<TagVO> getAll() {
        return tagRepository.findAll().stream().map(Tag::toVO).collect(Collectors.toList());
    }

    @Override
    public TagVO getById(int id) {
        Tag tag = tagRepository.findById(id);
        if(tag == null){
            throw BlueWhaleException.tagNotFound();
        }
        return tag.toVO();
    }

    @Override
    public TagVO addTag(TagVO tagVO) {
        Tag tag = tagRepository.findByName(tagVO.getName());
        if(tag!=null){
            throw BlueWhaleException.tagNameAlreadyExists();
        }
        Tag newTag = tagVO.toPO();
        tagRepository.save(newTag);
        return tagVO;
    }

    @Override
    public Boolean deleteTag(String id) {
        Tag tag = tagRepository.findById(Integer.parseInt(id));
        if(tag==null){
            return false;
        } 
        tagRepository.deleteById(Integer.parseInt(id));
        return true;
    }

    @Override
    public Boolean deleteBook(String tagId, String bookId) {
        Tag tag = tagRepository.findById(Integer.parseInt(tagId));
        if(tag==null){
            return false;
        } 
        if(!tag.getBooklist().contains(bookId)){
            return false;
        }
        tag.getBooklist().remove(bookId);
        tagRepository.save(tag);
        return true;
    }

    @Override
    public Boolean addBook(String tagId, String bookId) {
        Tag tag = tagRepository.findById(Integer.parseInt(tagId));
        if(tag==null){
            return false;
        } 
        if(tag.getBooklist().contains(bookId)){
            return false;
        }
        tag.getBooklist().add(bookId);
        tagRepository.save(tag);
        return true;
    }

    @Override
    public List<String> getBooks(String tagId) {
        Tag tag = tagRepository.findById(Integer.parseInt(tagId));
        if(tag==null){
            throw BlueWhaleException.tagNotFound();
        }
        return tag.getBooklist();
    }

    @Override
    public List<Integer> getTagsOfBook(String productId) {
        List<Tag> tags= tagRepository.findAll();
        List<Integer> result = new ArrayList<Integer>();
        for(Tag tag : tags){
            if(tag.getBooklist().contains(productId)){
                result.add(tag.getId());
            }
        }
        return result;
    }

   
}