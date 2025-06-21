package com.seecoder.TomatoMall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seecoder.TomatoMall.service.AdvertisementService;
import com.seecoder.TomatoMall.vo.AdvertisementVO;
import com.seecoder.TomatoMall.vo.ResultVO;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {
    @Autowired
    AdvertisementService advertisementService;

    @GetMapping
     public ResultVO<List<AdvertisementVO>> getAll(){
        return ResultVO.buildSuccess(advertisementService.getAll());
    }

    @PutMapping
    public ResultVO<String> update(@RequestBody AdvertisementVO advertisementVO){
        if(advertisementService.updateInformation(advertisementVO)){
            return ResultVO.buildSuccess("更新成功");
        }
        return null;
    }

    @PostMapping
    public ResultVO<AdvertisementVO> add(@RequestBody AdvertisementVO advertisementVO){
        return ResultVO.buildSuccess(advertisementService.addAdvertisement(advertisementVO));
    }

    @DeleteMapping("/{id}")
    public ResultVO<String> delete(@PathVariable int id){
        if(advertisementService.deleteAdvertisement(id)){
            return ResultVO.buildSuccess("删除成功");
        }
        return null;
    }
}
