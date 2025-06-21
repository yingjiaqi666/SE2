package com.seecoder.TomatoMall.vo;

import java.util.List;
import com.seecoder.TomatoMall.po.Tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TagVO {

    private Integer id;

    private String name;

    private String description;

    private List<String> booklist;

    public Tag toPO(){
        Tag tag=new Tag();
        tag.setId(this.id);
        tag.setName(this.name);;
        tag.setDescription(this.description);
        tag.setBooklist(this.booklist);
        return tag;
    }
}
