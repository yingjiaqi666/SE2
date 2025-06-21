package com.seecoder.TomatoMall.po;


import com.seecoder.TomatoMall.vo.CORelationVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CORelation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "cartItemId")
    private Integer cartItemId;

    @Column(name = "orderId")
    private Integer orderId;

    public CORelationVO toVO(){
        CORelationVO coRelationVO = new CORelationVO();
        coRelationVO.setId(this.id);
        coRelationVO.setOrderId(this.orderId);
        coRelationVO.setCartItemId(this.cartItemId);
        return coRelationVO;
    }


}
