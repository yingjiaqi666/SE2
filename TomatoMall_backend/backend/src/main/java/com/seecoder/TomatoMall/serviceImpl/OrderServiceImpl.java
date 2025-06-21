package com.seecoder.TomatoMall.serviceImpl;

import com.seecoder.TomatoMall.po.Cart;
import com.seecoder.TomatoMall.po.Orders;
import com.seecoder.TomatoMall.po.User;
import com.seecoder.TomatoMall.repository.CartRepository;
import com.seecoder.TomatoMall.repository.OrdersRepository;
import com.seecoder.TomatoMall.service.OrderService;
import com.seecoder.TomatoMall.util.SecurityUtil;
import com.seecoder.TomatoMall.vo.Goods;
import com.seecoder.TomatoMall.vo.OrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    CartRepository cartRepository;

    @Override
    public List<OrdersVO> getAllOrders(){
        User user = securityUtil.getCurrentUser();
        Integer userId = user.getId();
        List<OrdersVO> ordersVOS = ordersRepository.findByUserId(userId).stream()
                .map(Orders::toVO)
                .collect(Collectors.toList());
        for(OrdersVO ordersVO : ordersVOS){
            List<String> cartIds = ordersVO.getCartItemIds();
            List<Goods> GoodsList = new ArrayList<>();
            for(String cartId : cartIds) {
                Cart cart = cartRepository.findById(Integer.parseInt(cartId)).get();
                Goods temp = new Goods();
                temp.setProductId(cart.getProductId());
                temp.setQuantity(cart.getQuantity());
                GoodsList.add(temp);
            }
            ordersVO.setGoodsList(GoodsList);
        }
        return ordersVOS;

    }
}
