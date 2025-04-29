package com.seecoder.TomatoMall.vo;

import com.seecoder.TomatoMall.po.CORelation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CORelationVO {
    private Integer id;

    private Integer cartItemId;

    private Integer orderId;

    public CORelation toPO(){
        CORelation coRelation = new CORelation();
        coRelation.setId(this.id);
        coRelation.setCartItemId(this.cartItemId);
        coRelation.setOrderId(this.orderId);
        return coRelation;
    }

}
