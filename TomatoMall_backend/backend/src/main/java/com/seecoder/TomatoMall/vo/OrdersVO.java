package com.seecoder.TomatoMall.vo;

import com.seecoder.TomatoMall.po.Orders;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrdersVO {
    private Integer orderId;
    private Integer userId;
    private List<String> cartItemIds;
    private String shipping_address;
    private BigDecimal amount;
    private String paymentMethod;
    private String status = "PENDING";
    private LocalDateTime createTime;
    private Integer quantity;
    private String username;

    public Orders toPO(){
        Orders orders = new Orders();
        orders.setOrderId(this.orderId);
        orders.setUserId(this.userId);
        orders.setAmount(this.amount);
        orders.setPaymentMethod(this.paymentMethod);
        orders.setStatus(this.status);
        orders.setCreateTime(this.createTime);
        orders.setQuantity(this.quantity);
        orders.setShipping_address(this.shipping_address);
        orders.setCartItemIds(this.cartItemIds);
        return orders;
    }

}
