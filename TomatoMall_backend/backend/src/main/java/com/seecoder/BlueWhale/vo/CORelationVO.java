package com.seecoder.BlueWhale.vo;

import com.seecoder.BlueWhale.po.CORelation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

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
