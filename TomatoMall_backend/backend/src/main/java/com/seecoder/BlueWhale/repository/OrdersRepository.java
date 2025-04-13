package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.po.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {
}
