package com.seecoder.TomatoMall.vo;

import com.seecoder.TomatoMall.enums.RoleEnum;
import com.seecoder.TomatoMall.po.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserVO {

    private Integer id;

    private String name;

    private String telephone;

    private String username;

    private String password;

    private String avatar;

    private String location;

    private RoleEnum role;

    private String email;

    public User toPO(){
        User user=new User();
        user.setId(this.id);
        user.setLocation(this.location);
        user.setName(this.name);
        user.setTelephone(this.telephone);
        user.setRole(this.role);
        user.setPassword(this.password);
        user.setAvatar(this.avatar);
        user.setUsername(this.username);
        user.setEmail(this.email);
        return user;
    }
}
