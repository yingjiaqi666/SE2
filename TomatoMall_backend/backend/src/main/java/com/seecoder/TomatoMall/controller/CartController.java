package com.seecoder.TomatoMall.controller;


import com.seecoder.TomatoMall.serviceImpl.CartServiceImpl;
import com.seecoder.TomatoMall.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResultVO<OrdersVO> commitOrder(@RequestBody CheckoutRequest request){
        return ResultVO.buildSuccess(cartServiceImpl.commitOrder(request));
    }


}
