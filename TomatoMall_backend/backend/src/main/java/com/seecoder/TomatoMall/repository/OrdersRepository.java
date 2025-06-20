package com.seecoder.TomatoMall.repository;

import com.seecoder.TomatoMall.po.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {
    long countByUserIdAndStatus(Integer userId, String status);
    Optional<Orders> findByOrderId(Integer orderId);
    List<Orders> findByStatusAndCreateTimeBefore(String pending, LocalDateTime cutoff);
}
