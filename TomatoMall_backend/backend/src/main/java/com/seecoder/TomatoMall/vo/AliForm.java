package com.seecoder.TomatoMall.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
public class AliForm {
    public String paymentForm;
    public String orderId;
    public BigDecimal totalAmount;
    public String paymentMethod;


}
