package com.seecoder.TomatoMall.po;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Stockpile {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "productid")
    private String productid;

    @Basic
    @Column(name = "amount")
    private int amount;

    @Basic
    @Column(name = "frozen")
    private int frozen;

    
}
