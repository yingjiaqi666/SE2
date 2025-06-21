package com.seecoder.TomatoMall.vo;

import com.seecoder.TomatoMall.po.Advertisement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdvertisementVO {
    private int id;
    private String title;
    private String content;
    private String imgUrl;
    private int productId;
    
    public Advertisement toPO(){
        Advertisement advertisement = new Advertisement();
        advertisement.setId(this.id);
        advertisement.setTitle(this.title);
        advertisement.setContent(this.content);
        advertisement.setImgUrl(this.imgUrl);
        advertisement.setProductId(this.productId);
        return advertisement;
    }
}
