package com.seecoder.TomatoMall.po;

import com.seecoder.TomatoMall.enums.RoleEnum;
import com.seecoder.TomatoMall.vo.UserVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "telephone")
    private String telephone;

    @Basic
    @Column(name = "password")
    private String password;

    //必须注意，在Java中用驼峰，在MySQL字段中用连字符_
    @Basic
    @Column(name = "avatar", length = 1024)  // 修改为1024或更大
    private String avatar;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "location")
    private String location;

    @Basic
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public UserVO toVO(){
        UserVO userVO=new UserVO();
        userVO.setId(this.id);
        userVO.setLocation(this.location);
        userVO.setName(this.name);
        userVO.setRole(this.role);
        userVO.setAvatar(this.avatar);
        userVO.setTelephone(this.telephone);
        userVO.setPassword(this.password);
        userVO.setUsername(this.username);
        userVO.setEmail(this.email);
        return userVO;
    }
}
