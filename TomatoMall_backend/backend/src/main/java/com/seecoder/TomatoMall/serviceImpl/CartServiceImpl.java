package com.seecoder.TomatoMall.serviceImpl;

import com.seecoder.TomatoMall.exception.TomatoMallException;
import com.seecoder.TomatoMall.po.*;
import com.seecoder.TomatoMall.repository.CartRepository;
import com.seecoder.TomatoMall.repository.OrdersRepository;
import com.seecoder.TomatoMall.repository.ProductRepository;
import com.seecoder.TomatoMall.repository.StockpileRepository;
import com.seecoder.TomatoMall.service.CartService;
import com.seecoder.TomatoMall.util.SecurityUtil;
import com.seecoder.TomatoMall.vo.CartListVO;
import com.seecoder.TomatoMall.vo.CartVO;
import com.seecoder.TomatoMall.vo.CheckoutRequest;
import com.seecoder.TomatoMall.vo.OrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrdersRepository  ordersRepository;

    @Autowired
    StockpileRepository stockpileRepository;

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    CartRepository cartRepository;

    @Override
    public CartVO addIntoCart(CartVO cartVO){
        int productId = cartVO.getProductId();
        Integer quantity = cartVO.getQuantity();
        if(productRepository.findById(productId) == null){
            throw TomatoMallException.productNotFound();
        }

        Product product = productRepository.findById(productId);
        Stockpile stockpile = stockpileRepository.findByProductid(String.valueOf(productId));
        if(quantity>stockpile.getAmount()){
            throw TomatoMallException.overStock();
        }
        else {
            // 检查是否已存在相同商品
            Cart existingCart = cartRepository.findByProductIdAndUserIdAndCommited(productId, securityUtil.getCurrentUser().getId(),"false");
            if (existingCart != null) {
                // 如果已存在，更新数量
                existingCart.setQuantity(existingCart.getQuantity() + quantity);
                cartRepository.save(existingCart);
                return cartVO;
            }


            Cart savedCart = cartVO.toPO();
            savedCart.setUserId(securityUtil.getCurrentUser().getId());
            Cart saveTemp = cartRepository.save(savedCart);
            cartVO.setCover(product.getCover());
            cartVO.setDetail(product.getDetail());
            cartVO.setPrice(product.getPrice());
            cartVO.setDescription(product.getDescription());
            cartVO.setTitle(product.getTitle());
            cartVO.setCartItemId(saveTemp.getCartItemId());
        }
        return cartVO;
    }

    @Override
    public boolean delete(String cartItemId){
        if(cartRepository.findByCartItemId(Integer.parseInt(cartItemId))==null)
            throw TomatoMallException.productNotInCart();
        cartRepository.deleteCartByCartItemId(Integer.parseInt(cartItemId));
        return true;
    }

    @Override
    public boolean update(String cartItemId, Integer quantity){
        Cart cart = cartRepository.findByCartItemId(Integer.parseInt(cartItemId));
        if(cart==null)
            throw TomatoMallException.productNotInCart();
        cart.setQuantity(quantity);
        cartRepository.save(cart);
        return true;
    }

    @Override
    public CartListVO getList(){
        CartListVO result = new CartListVO();
        if(cartRepository.findAll().isEmpty()){
            return result;
        }
        User user = securityUtil.getCurrentUser();
        Integer userId = user.getId();
        List<CartVO> cartList = cartRepository.findAll().stream().filter(cart -> userId.equals(cart.getUserId())).map(Cart::toVO).collect(Collectors.toList());
        result.setItems(cartList);
        result.setTotal(cartList.size());
        BigDecimal total = new BigDecimal(0);
        for(CartVO cartVO : cartList){
            total = total.add(cartVO.getPrice().multiply(BigDecimal.valueOf(cartVO.getQuantity())));
        }
        result.setTotalAmount(total);
        return result;
    }


    @Override
    public OrdersVO commitOrder(CheckoutRequest req) {
        User user = securityUtil.getCurrentUser();
        Integer userId = securityUtil.getCurrentUser().getId();

        long pendingCount = ordersRepository.countByUserIdAndStatus(userId, "PENDING");
        if (pendingCount >= 3) {
            throw TomatoMallException.unpaidOrderOversized();
        }

        CheckoutRequest.ShippingAddress shipping_address = req.getShipping_address();
        String payment_method = req.getPayment_method();


        List<String> cartItemIds = req.getCartItemIds();
        List<Integer> ids = cartItemIds.stream().map(Integer::valueOf).collect(Collectors.toList());
        List<Cart> carts = cartRepository.findAllById(ids);

        Iterator<Cart> iterator = carts.iterator();
        while (iterator.hasNext()) {
            Cart c = iterator.next();
            if (!c.getCommited().equals("false")) {
                iterator.remove();
                cartItemIds.remove(String.valueOf(c.getCartItemId()));
            }
        }


        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalQuantity = 0;
        for (Cart cart : carts) {
            Stockpile sp = stockpileRepository.findByProductid(String.valueOf(cart.getProductId()));
            if (sp.getAmount() < cart.getQuantity()) {
                throw TomatoMallException.overStock();
            }
        }
        // 锁定库存
        for (Cart cart : carts) {
            Stockpile sp = stockpileRepository.findByProductid(String.valueOf(cart.getProductId()));
            Product product = productRepository.findById(Integer.parseInt(String.valueOf(cart.getProductId())));
            sp.setAmount(sp.getAmount() - cart.getQuantity());
            sp.setFrozen(sp.getFrozen() + cart.getQuantity());
            stockpileRepository.save(sp);

            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
            totalQuantity += cart.getQuantity();
            cart.setCommited("true");
            cartRepository.save(cart); // 将已提交的商品标记为已提交
        }

        Orders order = new Orders();
        order.setUserId(userId);
        order.setCartItemIds(cartItemIds);
        order.setShipping_address(shipping_address.getAddress());
        order.setPaymentMethod(payment_method);
        order.setAmount(totalAmount);
        order.setQuantity(totalQuantity);
        order = ordersRepository.save(order);

        OrdersVO temp = order.toVO();
        temp.setUsername(user.getUsername());

        // 返回 VO
        return temp;
    }

}
