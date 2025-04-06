package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.po.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
