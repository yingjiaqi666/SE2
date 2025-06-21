package com.seecoder.TomatoMall.vo;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class CheckoutRequest {
    private List<String> cartItemIds;
    private ShippingAddress shipping_address; // 嵌套对象
    private String payment_method;

    @Data
    public static class ShippingAddress {
        private String name;
        private String phone;
        private String address;
        private String postal_code;
    }
}
