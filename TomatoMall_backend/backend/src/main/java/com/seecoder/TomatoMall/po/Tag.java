package com.seecoder.TomatoMall.po;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import javax.persistence.*;

import com.seecoder.TomatoMall.vo.TagVO;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Tag {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    
    @ElementCollection(targetClass = String.class)
    private List<String> booklist;


    public TagVO toVO(){
        TagVO tagVO=new TagVO();
        tagVO.setId(this.id);
        tagVO.setName(this.name);
        tagVO.setDescription(this.description);
        tagVO.setBooklist(this.booklist);
        return tagVO;
    }
}
