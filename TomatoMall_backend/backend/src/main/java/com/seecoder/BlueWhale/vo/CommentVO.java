package com.seecoder.BlueWhale.vo;

import java.util.Date;

import com.seecoder.BlueWhale.po.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CommentVO {
    private Integer id;
    private Integer bookId;
    private String comment;
    private String commentTitle;
    private String fatherId;
    private Integer userId;
    private Date time;

    public Comment toPO(){
        Comment comment=new Comment();
        comment.setId(this.id);
        comment.setBookId(this.bookId);;
        comment.setComment(this.comment);
        comment.setCommentTitle(this.commentTitle);
        comment.setFatherId(this.fatherId);
        comment.setUserId(this.userId);
        comment.setTime(this.time);
        return comment;
    }
}
