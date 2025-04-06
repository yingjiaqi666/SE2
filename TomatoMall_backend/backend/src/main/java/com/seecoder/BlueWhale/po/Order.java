package com.seecoder.BlueWhale.po;


import com.seecoder.BlueWhale.vo.CartVO;
import com.seecoder.BlueWhale.vo.OrderVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "orderId")
    private Integer orderId;

    @Column(name = "userId", nullable = false)
    private Integer userId;

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "PENDING";

    @Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    private Integer quantity;

    public OrderVO toVO(){
        OrderVO vo = new OrderVO();
        vo.setOrderId(this.orderId);
        vo.setUserId(this.userId);
        vo.setAmount(this.amount);
        vo.setPaymentMethod(this.paymentMethod);
        vo.setStatus(this.status);
        vo.setCreateTime(this.createTime);
        vo.setQuantity(this.quantity);
        return vo;
    }



}
