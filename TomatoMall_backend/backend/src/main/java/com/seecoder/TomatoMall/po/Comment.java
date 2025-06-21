package com.seecoder.TomatoMall.po;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;


import com.seecoder.TomatoMall.exception.TomatoMallException;
import com.seecoder.TomatoMall.serviceImpl.ProductServiceImpl;
import com.seecoder.TomatoMall.vo.CommentVO;
import com.seecoder.TomatoMall.vo.ProductVO;

import java.time.LocalDateTime;

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

    @Column(name = "create_time", updatable = false)
    private LocalDateTime time;

    @PrePersist
    protected void onCreate() {
        time = LocalDateTime.now(); // 代码层保证非空
    }


    public CommentVO toVO(){
        CommentVO vo = new CommentVO();
        vo.setId(this.id);
        vo.setBookId(this.bookId);
        vo.setComment(this.comment);
        vo.setCommentTitle(this.commentTitle);
        vo.setFatherId(this.fatherId);
        vo.setUserId(this.userId);
        vo.setTime(this.time);
        return vo;
    }

}
