package com.seecoder.BlueWhale.po;

import com.seecoder.BlueWhale.enums.RoleEnum;
import com.seecoder.BlueWhale.vo.UserVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Specification {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "item")
    private String item;

    @Basic
    @Column(name = "value")
    private String value;

    @Basic
    @Column(name = "productId")
    private String productId;
}
