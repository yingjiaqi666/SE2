package com.seecoder.BlueWhale.service;

import java.util.List;

import com.seecoder.BlueWhale.vo.AdvertisementVO;

public interface AdvertisementService {
    List<AdvertisementVO> getAll();

    Boolean updateInformation(AdvertisementVO advertisementVO);

    AdvertisementVO addAdvertisement(AdvertisementVO advertisementVO);

    Boolean deleteAdvertisement(int id);
}
