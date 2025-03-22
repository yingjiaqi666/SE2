package com.seecoder.BlueWhale.controller;

import com.seecoder.BlueWhale.service.ImageService;
import com.seecoder.BlueWhale.service.UserService;
import com.seecoder.BlueWhale.vo.ResultVO;
import com.seecoder.BlueWhale.vo.UserVO;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @PostMapping("/register")
    public ResultVO<Boolean> register(@RequestBody UserVO userVO){
        return ResultVO.buildSuccess(userService.register(userVO));
    }

    @PostMapping("/login")
    public ResultVO<String> login(@RequestParam("phone") String phone, @RequestParam("password") String password){
        return ResultVO.buildSuccess(userService.login(phone, password));
    }

    @PostMapping("/update_image")
    public ResultVO<Boolean> updateImage(@RequestParam("file") MultipartFile file){
        //TODO 将输入的文件转为url(前端传过来的应该是一个inputstream吧，我不是很确定)
        return ResultVO.buildSuccess(imageService.upload(file));
    }

    @GetMapping("/get_image")
    public ResultVO<String> getImage(){
        return ResultVO.buildSuccess("");
    }



    @GetMapping
    public ResultVO<UserVO> getInformation(){
        return ResultVO.buildSuccess(userService.getInformation());
    }

    @PostMapping
    public ResultVO<Boolean> updateInformation(@RequestBody UserVO userVO){
        return ResultVO.buildSuccess(userService.updateInformation(userVO));
    }
}
