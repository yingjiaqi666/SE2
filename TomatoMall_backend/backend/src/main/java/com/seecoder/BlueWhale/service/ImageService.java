package com.seecoder.BlueWhale.service;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public Boolean upload(MultipartFile file);
    public String getImage(String object);
}
