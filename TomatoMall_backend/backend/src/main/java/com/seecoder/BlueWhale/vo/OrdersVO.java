package com.seecoder.BlueWhale.vo;

import com.seecoder.BlueWhale.po.Orders;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class OrdersVO {
    private Integer orderId;
    private Integer userId;
    private BigDecimal amount;
    private String paymentMethod;
    private String status = "PENDING";
    private LocalDateTime createTime;
    private Integer quantity;

    public Orders toPO(){
        Orders orders = new Orders();
        orders.setOrderId(this.orderId);
        orders.setUserId(this.userId);
        orders.setAmount(this.amount);
        orders.setPaymentMethod(this.paymentMethod);
        orders.setStatus(this.status);
        orders.setCreateTime(this.createTime);
        orders.setQuantity(this.quantity);
        return orders;
    }

}
