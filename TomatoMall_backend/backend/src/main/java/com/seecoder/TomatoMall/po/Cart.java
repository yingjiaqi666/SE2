package com.seecoder.TomatoMall.po;


import com.seecoder.TomatoMall.vo.CartVO;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    private Product product;

    @Basic
    @Column(name = "productId")
    private Integer productId;

    @Basic
    @Column(name = "quantity")
    private Integer quantity;

    @Transient
    public BigDecimal getPrice() {
        return product != null ? product.getPrice() : null;
    }

    @Transient
    public String getTitle(){
        return product != null ? product.getTitle(): null;
    }


    public CartVO toVO(){
        CartVO vo = new CartVO();
        vo.setCartItemId(this.cartItemId);
        vo.setUserId(this.userId);
        vo.setProductId(this.productId);
        vo.setQuantity(this.quantity);
        vo.setPrice(this.getPrice());
        vo.setTitle(this.getTitle());
        return vo;
    }

}
