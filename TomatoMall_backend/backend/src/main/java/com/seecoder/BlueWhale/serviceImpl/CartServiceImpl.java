package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.repository.CartRepository;
import com.seecoder.BlueWhale.repository.ProductRepository;
import com.seecoder.BlueWhale.service.CartService;
import com.seecoder.BlueWhale.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    public CartVO addIntoCart(CartVO cartVO){
        int productId = cartVO.getProductId();
        Integer quantity = cartVO.getQuantity();
        if(productRepository.findById(productId) == null){
            throw BlueWhaleException.productNotFound();
        }

        Product product = productRepository.findById(productId);
        if(quantity>product)
        return null;
    }

}
