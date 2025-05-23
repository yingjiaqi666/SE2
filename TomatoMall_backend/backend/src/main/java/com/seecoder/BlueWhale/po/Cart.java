package com.seecoder.BlueWhale.po;


import com.seecoder.BlueWhale.vo.CartVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cart {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cartItemId")
    private Integer cartItemId;

    @Basic
    @Column(name = "userId")
    private Integer userId;

    @Basic
    @Column(name = "productId")
    private Integer productId;

    @Basic
    @Column(name = "quantity")
    private Integer quantity;


    public CartVO toVO(){
        CartVO vo = new CartVO();
        vo.setCartItemId(this.cartItemId);
        vo.setUserId(this.userId);
        vo.setProductId(this.productId);
        vo.setQuantity(this.quantity);
        return vo;
    }

}
