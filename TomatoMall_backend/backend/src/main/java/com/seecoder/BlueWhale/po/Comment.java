package com.seecoder.BlueWhale.po;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;
import com.seecoder.BlueWhale.vo.CommentVO;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "book_id")
    private Integer bookId;

    @Basic
    @Column(name = "comment")
    private String comment;
    
    @Basic
    @Column(name = "comment_title")
    private String commentTitle;

    @Basic
    @Column(name = "father_id")
    private String fatherId;

    @Basic
    @Column(name = "user_id")
    private Integer userId;

    @Basic
    @Column(name = "time")
    private Date time;

    public CommentVO toVO(){
        
        CommentVO vo = new CommentVO();
        vo.setId(this.id);
        vo.setBookId(this.bookId);
        vo.setComment(this.comment);
        vo.setCommentTitle(this.commentTitle);
        vo.setFatherId(this.fatherId);
        vo.setUserId(this.userId);
        vo.setTime(time);
        return vo;
    }

}
