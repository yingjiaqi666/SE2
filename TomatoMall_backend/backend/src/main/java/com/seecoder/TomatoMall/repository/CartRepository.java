package com.seecoder.TomatoMall.repository;

import com.seecoder.TomatoMall.po.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Cart findByCartItemId(Integer cartItemId);
    void deleteCartByCartItemId(Integer cartItemId);
}
