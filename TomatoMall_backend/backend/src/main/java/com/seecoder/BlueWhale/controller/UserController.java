package com.seecoder.BlueWhale.controller;

import com.seecoder.BlueWhale.service.ImageService;
import com.seecoder.BlueWhale.service.UserService;
import com.seecoder.BlueWhale.vo.ResultVO;
import com.seecoder.BlueWhale.vo.UserVO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/accounts")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;


    @PostMapping
    public ResultVO<String> register(@RequestBody UserVO userVO){
        if(userService.register(userVO))
            return ResultVO.buildSuccess("注册成功");
        return null; //一定不会到这一步，因为false就会抛出异常自动buildFailure
    }

    @PostMapping("/login")
    public ResultVO<String> login(@RequestBody UserVO userVO){
        return ResultVO.buildSuccess(userService.login(userVO.getUsername(), userVO.getPassword()));
    }

    @PostMapping("/update_image")
    public ResultVO<Boolean> updateImage(@RequestParam("file") MultipartFile file){
        return ResultVO.buildSuccess(imageService.upload(file));
    }

    @GetMapping("/get_image")
    public ResultVO<String> getImage(){
        return ResultVO.buildSuccess(userService.getImage());
    }


    @GetMapping("/{username}")
    public ResultVO<UserVO> getInformation(@PathVariable String username){
        return ResultVO.buildSuccess(userService.getInformation(username));
    }

    @PutMapping
    public ResultVO<String> updateInformation(@RequestBody UserVO userVO){
        if(userService.updateInformation(userVO))
            return ResultVO.buildSuccess("更新成功");
        return null;//不会来这一步
    }
}
