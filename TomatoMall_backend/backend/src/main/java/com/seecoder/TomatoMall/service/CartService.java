package com.seecoder.TomatoMall.service;

import com.seecoder.TomatoMall.vo.CartListVO;
import com.seecoder.TomatoMall.vo.CartVO;
import com.seecoder.TomatoMall.vo.CheckoutRequest;
import com.seecoder.TomatoMall.vo.OrdersVO;

public interface CartService {
    public CartVO addIntoCart(CartVO cartVO);
    public boolean delete(String cartItemId);
    public boolean update(String cartItemId, Integer quantity);
    public CartListVO getList();
    public OrdersVO commitOrder(CheckoutRequest req);
}
