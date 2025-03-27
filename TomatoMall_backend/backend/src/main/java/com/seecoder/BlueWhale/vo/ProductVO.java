package com.seecoder.BlueWhale.vo;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.seecoder.BlueWhale.enums.RoleEnum;
import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.po.Specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ProductVO {

    private Integer id;

    private String title;

    private BigDecimal price;

    private String rate;

    private String discription;

    private String cover;

    private String detail;

    private List<Specification> specifications;

    public Product toPO(){
        Product product=new Product();
        product.setId(this.id);
        product.setTitle(this.title);;
        product.setPrice(this.price);
        product.setRate(this.rate);
        product.setDiscription(this.discription);
        product.setCover(this.cover);
        product.setDetail(this.detail);
        product.setSpecifications(this.specifications);
        return product;
    }
}
