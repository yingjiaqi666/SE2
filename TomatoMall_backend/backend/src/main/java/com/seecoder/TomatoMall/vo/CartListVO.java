package com.seecoder.TomatoMall.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartListVO {

    private List<CartVO> items = new ArrayList<>();

    private Integer total;

    private BigDecimal totalAmount;
}
