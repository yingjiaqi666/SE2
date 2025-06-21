package com.seecoder.TomatoMall.serviceImpl;

import com.seecoder.TomatoMall.exception.TomatoMallException;
import com.seecoder.TomatoMall.po.User;
import com.seecoder.TomatoMall.repository.UserRepository;
import com.seecoder.TomatoMall.service.ImageService;
import com.seecoder.TomatoMall.service.UserService;
import com.seecoder.TomatoMall.util.SecurityUtil;
import com.seecoder.TomatoMall.util.TokenUtil;
import com.seecoder.TomatoMall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author: GaoZhaolong
 * @Date: 14:46 2023/11/26
 *
 * 注册登录功能实现
*/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    ImageService imageService;


    @Override
    public Boolean register(UserVO userVO) {
        User user = userRepository.findByUsername(userVO.getUsername());
        if (user != null) {
            throw TomatoMallException.userNameAlreadyExists();
        }
        User newUser = userVO.toPO();
        userRepository.save(newUser);
        return true;
    }

    @Override
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw TomatoMallException.LoginError();
        }else {
            if(!password.equals(user.getPassword())){
                throw TomatoMallException.LoginError();
            }
        }
        return tokenUtil.getToken(user);
    }

    @Override
    public UserVO getInformation(String username) {
        User user=userRepository.findByUsername(username);
        return user.toVO();
    }

    @Override
    public Boolean updateInformation(UserVO userVO) {
        User user=userRepository.findByUsername(userVO.getUsername());
        if(user ==null){
            throw TomatoMallException.userNameNotFound();
        }
        if(userVO.getAvatar()!=null)
            user.setAvatar(userVO.getAvatar());

        if(userVO.getName()!=null)
            user.setName(userVO.getName());

        if(userVO.getPassword()!=null)
            user.setPassword(userVO.getPassword());

        if(userVO.getRole()!=null)
            user.setRole(userVO.getRole());

        if(userVO.getTelephone()!=null)
            user.setTelephone(userVO.getTelephone());

        if(userVO.getEmail()!=null)
            user.setEmail(userVO.getEmail());

        if(userVO.getLocation()!=null)
            user.setLocation(userVO.getLocation());

        userRepository.save(user);
        return true;
    }


    public Boolean updateImage(String url) {
        User user=securityUtil.getCurrentUser();
        if(url!=null){
            user.setAvatar(url);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public String getImage(){
        String url = securityUtil.getCurrentUser().getAvatar();
        return imageService.getImage(url);
    }



}
