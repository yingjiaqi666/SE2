package com.seecoder.TomatoMall.po;

import javax.persistence.*;

import com.seecoder.TomatoMall.vo.AdvertisementVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Advertisement {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "content")
    private String content;

    @Basic
    @Column(name = "imgUrl")
    private String imgUrl;

    @Basic
    @Column(name = "productId")
    private int productId;

    public AdvertisementVO toVO(){
        AdvertisementVO advertisementVO = new AdvertisementVO();
        advertisementVO.setId(this.id);
        advertisementVO.setTitle(this.title);
        advertisementVO.setContent(this.content);
        advertisementVO.setImgUrl(this.imgUrl);
        advertisementVO.setProductId(this.productId);
        return advertisementVO;
    }
    
}
