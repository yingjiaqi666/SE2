package com.seecoder.BlueWhale.vo;

import com.seecoder.BlueWhale.po.Cart;
import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.repository.ProductRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CartVO {

    private Integer cartItemId;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    //下面为返回前端的额外信息
    private String title;
    private String description;
    private String cover;
    private String detail;
    private BigDecimal price;

    public Cart toPO(){
        Cart cart = new Cart();
        cart.setCartItemId(this.cartItemId);
        cart.setUserId(this.userId);
        cart.setProductId(this.productId);
        cart.setQuantity(this.quantity);
        return cart;
    }

}
