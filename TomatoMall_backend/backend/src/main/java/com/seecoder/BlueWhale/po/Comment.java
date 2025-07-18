package com.seecoder.BlueWhale.po;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;


import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.serviceImpl.ProductServiceImp;
import com.seecoder.BlueWhale.vo.CommentVO;
import com.seecoder.BlueWhale.vo.ProductVO;

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

    public CommentVO toVO(){
        ProductServiceImp productService = new ProductServiceImp();
        CommentVO vo = new CommentVO();
        ProductVO book = productService.getById(this.bookId);
        if(book == null){
            throw BlueWhaleException.productNotFound();
        }
        vo.setId(this.id);
        vo.setBookId(this.bookId);
        vo.setComment(this.comment);
        vo.setCommentTitle(this.commentTitle);
        vo.setFatherId(this.fatherId);
        vo.setUserId(this.userId);
        vo.setBookTitle(book.getTitle());
        vo.setCover(book.getCover());
        return vo;
    }

}
