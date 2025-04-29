package com.seecoder.TomatoMall.repository;

import com.seecoder.TomatoMall.po.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {
}
