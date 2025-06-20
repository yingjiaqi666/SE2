package com.seecoder.BlueWhale.vo;

import java.util.List;
import com.seecoder.BlueWhale.po.Tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TagVO {

    private Integer id;

    private String name;

    private String describe;

    private List<String> booklist;

    public Tag toPO(){
        Tag tag=new Tag();
        tag.setId(this.id);
        tag.setName(this.name);;
        tag.setDescribe(this.describe);
        tag.setBooklist(this.booklist);
        return tag;
    }
}
