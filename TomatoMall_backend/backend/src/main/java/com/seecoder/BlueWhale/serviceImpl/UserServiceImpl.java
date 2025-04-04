package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.User;
import com.seecoder.BlueWhale.repository.UserRepository;
import com.seecoder.BlueWhale.service.ImageService;
import com.seecoder.BlueWhale.service.UserService;
import com.seecoder.BlueWhale.util.SecurityUtil;
import com.seecoder.BlueWhale.util.TokenUtil;
import com.seecoder.BlueWhale.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


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
        User user = userRepository.findByTelephone(userVO.getTelephone());
        if (user != null) {
            throw BlueWhaleException.phoneAlreadyExists();
        }
        User newUser = userVO.toPO();
        userRepository.save(newUser);
        return true;
    }

    @Override
    public String login(String phone, String password) {
        User user = userRepository.findByTelephoneAndPassword(phone, password);
        if (user == null) {
            throw BlueWhaleException.phoneOrPasswordError();
        }
        return tokenUtil.getToken(user);
    }

    @Override
    public UserVO getInformation(String username) {
        // User user=securityUtil.getCurrentUser();
        User user=userRepository.findByUsername(username);
        return user.toVO();
    }

    @Override
    public Boolean updateInformation(UserVO userVO) {
        User user=securityUtil.getCurrentUser();
        if (userVO.getPassword()!=null){
            user.setPassword(userVO.getPassword());
        }
        if (userVO.getName()!=null){
            user.setName(userVO.getName());
        }
        if (userVO.getLocation()!=null){
            user.setLocation(userVO.getLocation());
        }
        userRepository.save(user);
        return true;
    }


    public Boolean updateImage(String url) {
        User user=securityUtil.getCurrentUser();
        if(url!=null){
            user.setAvatar(url);
            return true;
        }
        return false;
    }

    public String getImage(){
        String url = securityUtil.getCurrentUser().getAvatar();
        return imageService.getImage(url);
    }



}
