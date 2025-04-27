package com.seecoder.BlueWhale.service;

import com.seecoder.BlueWhale.vo.CartListVO;
import com.seecoder.BlueWhale.vo.CartVO;
import com.seecoder.BlueWhale.vo.CheckoutRequest;
import com.seecoder.BlueWhale.vo.OrdersVO;

import java.util.List;

public interface CartService {
    public CartVO addIntoCart(CartVO cartVO);
    public boolean delete(String cartItemId);
    public boolean update(String cartItemId, Integer quantity);
    public CartListVO getList();
    public OrdersVO commitOrder(CheckoutRequest req);
}
