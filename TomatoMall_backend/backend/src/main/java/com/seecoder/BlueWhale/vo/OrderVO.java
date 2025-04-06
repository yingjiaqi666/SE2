package com.seecoder.BlueWhale.vo;

import com.seecoder.BlueWhale.po.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class OrderVO {
    private Integer orderId;
    private Integer userId;
    private BigDecimal amount;
    private String paymentMethod;
    private String status = "PENDING";
    private LocalDateTime createTime;
    private Integer quantity;

    public Order toPO(){
        Order order = new Order();
        order.setOrderId(this.orderId);
        order.setUserId(this.userId);
        order.setAmount(this.amount);
        order.setPaymentMethod(this.paymentMethod);
        order.setStatus(this.status);
        order.setCreateTime(this.createTime);
        order.setQuantity(this.quantity);
        return order;
    }

}
