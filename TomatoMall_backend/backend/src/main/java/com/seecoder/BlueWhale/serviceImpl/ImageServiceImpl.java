package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.service.ImageService;
import com.seecoder.BlueWhale.service.UserService;
import com.seecoder.BlueWhale.util.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.seecoder.BlueWhale.exception.BlueWhaleException;
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
            throw BlueWhaleException.fileUploadFail();
        }
    }

    @Override
    public String getImage(String object){
        try {
            return ossUtil.generatePresignedUrl(object);
        }catch (Exception e){
            throw BlueWhaleException.genFail();
        }
    }

}
