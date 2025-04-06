package com.seecoder.BlueWhale.controller;


import com.seecoder.BlueWhale.serviceImpl.CartServiceImpl;
import com.seecoder.BlueWhale.vo.CartVO;
import com.seecoder.BlueWhale.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartServiceImpl cartServiceImpl;

    @GetMapping
    public ResultVO<CartVO> addIntoCart(@RequestBody CartVO cartVO){

        return ResultVO.buildSuccess(cartVO);
    }


}
