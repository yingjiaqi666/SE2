package com.seecoder.TomatoMall.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seecoder.TomatoMall.exception.BlueWhaleException;
import com.seecoder.TomatoMall.po.Advertisement;
import com.seecoder.TomatoMall.repository.AdvertisementRepository;
import com.seecoder.TomatoMall.repository.ProductRepository;
import com.seecoder.TomatoMall.service.AdvertisementService;
import com.seecoder.TomatoMall.vo.AdvertisementVO;

@Service
public class AdvertisementServiceImp implements AdvertisementService{

    @Autowired
    AdvertisementRepository advertisementRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<AdvertisementVO> getAll() {
        return advertisementRepository.findAll().stream().map(Advertisement::toVO).collect(Collectors.toList());
    }

    @Override
    public Boolean updateInformation(AdvertisementVO advertisementVO) {
        int id = advertisementVO.getId();
        Advertisement target = advertisementRepository.findById(id);
        if(target == null||productRepository.findById(advertisementVO.getProductId())==null){
            throw BlueWhaleException.productNotFound();
        }
        target.setProductId(advertisementVO.getProductId());
        if(advertisementVO.getContent()!=null){
            target.setContent(advertisementVO.getContent());
        }
        if(advertisementVO.getImgUrl()!=null){
            target.setImgUrl(advertisementVO.getImgUrl());
        }
        if(advertisementVO.getTitle()!=null){
            target.setTitle(advertisementVO.getTitle());
        }
        advertisementRepository.save(target);
        return true;
    }

    @Override
    public AdvertisementVO addAdvertisement(AdvertisementVO advertisementVO) {
        if(productRepository.findById(advertisementVO.getProductId())==null){
            throw BlueWhaleException.productNotFound();
        }
        Advertisement newAdvertisement = advertisementVO.toPO();
        Advertisement savePO = advertisementRepository.save(newAdvertisement);
        return savePO.toVO();
    }

    @Override
    public Boolean deleteAdvertisement(int id) {
        if(advertisementRepository.findById(id)==null){
            throw BlueWhaleException.productNotFound();
        }
        advertisementRepository.deleteById(id);
        return true;
    }

    
}
