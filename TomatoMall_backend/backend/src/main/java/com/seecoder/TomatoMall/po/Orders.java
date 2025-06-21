package com.seecoder.TomatoMall.po;


import com.seecoder.TomatoMall.vo.OrdersVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Orders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "orderId")
    private Integer orderId;

    @Column(name = "userId", nullable = false)
    private Integer userId;

    @ElementCollection
    @CollectionTable(name = "order_cart_items", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "cart_item_ids")
    private List<String> cartItemIds;

    @Column(name ="shipping_address")
    private String shipping_address;

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "PENDING";

    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now(); // 代码层保证非空
    }

    private Integer quantity;

    public OrdersVO toVO(){
        OrdersVO vo = new OrdersVO();
        vo.setOrderId(this.orderId);
        vo.setUserId(this.userId);
        vo.setAmount(this.amount);
        vo.setPaymentMethod(this.paymentMethod);
        vo.setStatus(this.status);
        vo.setCreateTime(this.createTime);
        vo.setCartItemIds(this.cartItemIds);
        vo.setShipping_address(this.shipping_address);
        vo.setQuantity(this.quantity);
        return vo;
    }



}
