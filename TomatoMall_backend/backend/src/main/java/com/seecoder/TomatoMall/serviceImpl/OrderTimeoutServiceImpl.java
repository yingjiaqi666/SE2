package com.seecoder.TomatoMall.serviceImpl;


import com.seecoder.TomatoMall.po.Cart;
import com.seecoder.TomatoMall.po.Orders;
import com.seecoder.TomatoMall.po.Stockpile;
import com.seecoder.TomatoMall.repository.CartRepository;
import com.seecoder.TomatoMall.repository.OrdersRepository;
import com.seecoder.TomatoMall.repository.StockpileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderTimeoutServiceImpl {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private StockpileRepository stockpileRepository;
    @Autowired
    private CartRepository cartRepository;

    @Scheduled(fixedRate = 1000)
    @Transactional
    public void scanAndExpirePendingOrders() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(30);
        List<Orders> expired = ordersRepository
                .findByStatusAndCreateTimeBefore("PENDING", cutoff);
        if(expired.isEmpty()) {
            return;
        }
        for (Orders order : expired) {
            // 1) 回滚库存：amount += frozen, frozen = 0
            List<String> cartItemIds = order.getCartItemIds();
            for (String cartId : cartItemIds) {
                Cart cart = cartRepository.findByCartItemId(Integer.valueOf(cartId));
                Stockpile sp = stockpileRepository.findByProductid(
                        String.valueOf(cart.getProductId()));
                int qty = cart.getQuantity();
                sp.setAmount(sp.getAmount() + qty);
                sp.setFrozen(sp.getFrozen() - qty);
                stockpileRepository.save(sp);
            }
            // 2) 更新订单状态
            order.setStatus("OVERTIME");
            ordersRepository.save(order);
        }
    }
}
