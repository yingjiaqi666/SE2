package com.seecoder.TomatoMall.service;

import java.util.List;

import com.seecoder.TomatoMall.vo.AdvertisementVO;

public interface AdvertisementService {
    List<AdvertisementVO> getAll();

    Boolean updateInformation(AdvertisementVO advertisementVO);

    AdvertisementVO addAdvertisement(AdvertisementVO advertisementVO);

    Boolean deleteAdvertisement(int id);
}
