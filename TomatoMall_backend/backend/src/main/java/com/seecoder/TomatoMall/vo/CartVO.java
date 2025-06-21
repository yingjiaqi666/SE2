package com.seecoder.TomatoMall.vo;

import com.seecoder.TomatoMall.po.Cart;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CartVO {

    private Integer cartItemId;

    private Integer userId;

    private Integer productId;

    private Integer quantity;
    private BigDecimal price;
    private String title;
    //下面为返回前端的额外信息

    private String description;
    private String cover;
    private String detail;

    public Cart toPO(){
        Cart cart = new Cart();
        cart.setCartItemId(this.cartItemId);
        cart.setUserId(this.userId);
        cart.setProductId(this.productId);
        cart.setQuantity(this.quantity);
        return cart;
    }

}
