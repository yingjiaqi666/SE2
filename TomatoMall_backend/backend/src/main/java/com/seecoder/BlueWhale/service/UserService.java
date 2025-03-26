package com.seecoder.BlueWhale.service;

import com.seecoder.BlueWhale.vo.UserVO;

public interface UserService {
    Boolean register(UserVO userVO);

    String login(String phone,String password);

    UserVO getInformation(String username);

    Boolean updateInformation(UserVO userVO);

    Boolean updateImage(String url);

    public String getImage();
}
