package com.seecoder.BlueWhale.controller;


import com.seecoder.BlueWhale.po.Orders;
import com.seecoder.BlueWhale.serviceImpl.CartServiceImpl;
import com.seecoder.BlueWhale.vo.CartListVO;
import com.seecoder.BlueWhale.vo.CartVO;
import com.seecoder.BlueWhale.vo.OrdersVO;
import com.seecoder.BlueWhale.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartServiceImpl cartServiceImpl;

    @PostMapping
    public ResultVO<CartVO> addIntoCart(@RequestBody CartVO cartVO){
        return ResultVO.buildSuccess(cartServiceImpl.addIntoCart(cartVO));
    }

    @DeleteMapping("/{cartItemId}")
    public ResultVO<String> deleteFromCart(@PathVariable String cartItemId){
        if(cartServiceImpl.delete(cartItemId))
            return ResultVO.buildSuccess("删除成功");
        return null;
    }

    @PatchMapping("/{cartItemId}")
    public ResultVO<String> updateCart(@PathVariable String cartItemId, @RequestBody CartVO cartVO) {
        if (cartServiceImpl.update(cartItemId,cartVO.getQuantity()))
            return ResultVO.buildSuccess("修改数量成功");
        return null;
    }

    @GetMapping
    public ResultVO<CartListVO> getList(){
        return ResultVO.buildSuccess(cartServiceImpl.getList());
    }

    @PostMapping("/checkout")
    public ResultVO<OrdersVO> commitOrder(@RequestBody List<String> cartItemIds,@RequestParam String shipping_address,@RequestParam String payment_method){
        return ResultVO.buildSuccess(cartServiceImpl.commitOrder(cartItemIds,shipping_address,payment_method));
    }


}
