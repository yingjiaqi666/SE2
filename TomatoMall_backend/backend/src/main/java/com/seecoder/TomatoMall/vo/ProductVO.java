package com.seecoder.TomatoMall.vo;

import java.math.BigDecimal;
import java.util.List;

import com.seecoder.TomatoMall.po.Product;
import com.seecoder.TomatoMall.po.Specification;

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

    private String description;

    private String cover;

    private String detail;

    private List<Specification> specifications;

    public Product toPO(){
        Product product=new Product();
        product.setId(this.id);
        product.setTitle(this.title);;
        product.setPrice(this.price);
        product.setRate(this.rate);
        product.setDescription(this.description);
        product.setCover(this.cover);
        product.setDetail(this.detail);
        product.setSpecifications(this.specifications);

        if (this.specifications != null) {
            // 遍历每个 Specification 对象，并设置所属 Product
            this.specifications.forEach(spec -> {
                spec.setProduct(product);
            });
            product.setSpecifications(this.specifications);
        }
        return product;
    }
}
