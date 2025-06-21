package com.seecoder.TomatoMall.controller;

import com.seecoder.TomatoMall.service.ImageService;
import com.seecoder.TomatoMall.service.UserService;
import com.seecoder.TomatoMall.vo.ResultVO;
import com.seecoder.TomatoMall.vo.UserVO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/accounts")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResultVO<String> register(@RequestBody UserVO userVO){
        if(userService.register(userVO))
            return ResultVO.buildSuccess("注册成功");
        return null;
    }

    @PostMapping("/login")
    public ResultVO<String> login(@RequestBody UserVO userVO){
        return ResultVO.buildSuccess(userService.login(userVO.getUsername(), userVO.getPassword()));
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
        System.out.println("here:"+userVO);
        if(userService.updateInformation(userVO))
            return ResultVO.buildSuccess("更新成功");
        return null;
    }
}
