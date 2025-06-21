package com.seecoder.TomatoMall.controller;

import com.seecoder.TomatoMall.serviceImpl.ImageServiceImpl;
import com.seecoder.TomatoMall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    ImageServiceImpl imageService;

    @PostMapping("/update_image")
    public ResultVO<Boolean> updateImage(@RequestParam("file") MultipartFile file){
        return ResultVO.buildSuccess(imageService.upload(file));
    }
}
