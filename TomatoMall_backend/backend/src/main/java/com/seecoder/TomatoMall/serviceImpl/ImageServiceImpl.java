package com.seecoder.TomatoMall.serviceImpl;

import com.seecoder.TomatoMall.service.ImageService;
import com.seecoder.TomatoMall.service.UserService;
import com.seecoder.TomatoMall.util.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.seecoder.TomatoMall.exception.TomatoMallException;
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    OssUtil ossUtil;

    @Autowired
    UserService userService;

    @Override
    public Boolean upload(MultipartFile file) {
        try {
            String url = ossUtil.upload(file.getOriginalFilename(),file.getInputStream());
            return userService.updateImage(url);
        }catch (Exception e){
            throw TomatoMallException.fileUploadFail();
        }
    }

    @Override
    public String getImage(String object){
        try {
            return ossUtil.generatePresignedUrl(object);
        }catch (Exception e){
            throw TomatoMallException.genFail();
        }
    }

}
