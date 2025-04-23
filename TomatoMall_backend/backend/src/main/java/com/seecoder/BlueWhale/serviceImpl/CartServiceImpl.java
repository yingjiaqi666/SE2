package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.Cart;
import com.seecoder.BlueWhale.po.Orders;
import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.po.Stockpile;
import com.seecoder.BlueWhale.repository.CartRepository;
import com.seecoder.BlueWhale.repository.OrdersRepository;
import com.seecoder.BlueWhale.repository.ProductRepository;
import com.seecoder.BlueWhale.repository.StockpileRepository;
import com.seecoder.BlueWhale.service.CartService;
import com.seecoder.BlueWhale.util.SecurityUtil;
import com.seecoder.BlueWhale.vo.CartListVO;
import com.seecoder.BlueWhale.vo.CartVO;
import com.seecoder.BlueWhale.vo.OrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
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

    public CartVO addIntoCart(CartVO cartVO){
        int productId = cartVO.getProductId();
        Integer quantity = cartVO.getQuantity();
        if(productRepository.findById(productId) == null){
            throw BlueWhaleException.productNotFound();
        }

        Product product = productRepository.findById(productId);
        Stockpile stockpile = stockpileRepository.findByProductid(String.valueOf(productId));
        if(quantity>stockpile.getAmount()){
            throw BlueWhaleException.overStock();
        }
        else {
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

    public boolean delete(String cartItemId){
        if(cartRepository.findByCartItemId(Integer.parseInt(cartItemId))==null)
            throw BlueWhaleException.productNotInCart();
        cartRepository.deleteCartByCartItemId(Integer.parseInt(cartItemId));
        return true;
    }

    public boolean update(String cartItemId, Integer quantity){
        Cart cart = cartRepository.findByCartItemId(Integer.parseInt(cartItemId));
        if(cart==null)
            throw BlueWhaleException.productNotInCart();
        cart.setQuantity(quantity);
        cartRepository.save(cart);
        return true;
    }

    public CartListVO getList(){
        CartListVO result = new CartListVO();
        List<CartVO> cartList = cartRepository.findAll().stream().map(Cart::toVO).collect(Collectors.toList());
        result.setItems(cartList);
        result.setTotal(cartList.size());
        BigDecimal total = new BigDecimal(0);
        for(CartVO cartVO : cartList){
            total = total.add(cartVO.getPrice().multiply(BigDecimal.valueOf(cartVO.getQuantity())));
        }
        result.setTotalAmount(total);
        return result;
    }


    public OrdersVO commitOrder(List<String> cartItemIds, String shipping_address, String payment_method) {
        // 1. 获取并校验购物车项
        List<Integer> ids = cartItemIds.stream().map(Integer::valueOf).collect(Collectors.toList());
        List<Cart> carts = cartRepository.findAllById(ids);
        if (carts.size() != ids.size()) {
            throw BlueWhaleException.productNotInCart();
        }

        // 2. 校验库存并锁定
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalQuantity = 0;
        for (Cart cart : carts) {
            Stockpile sp = stockpileRepository.findByProductid(String.valueOf(cart.getProductId()));
            Product product = productRepository.findById(Integer.parseInt(String.valueOf(cart.getProductId())));
            if (sp.getAmount() < cart.getQuantity()) {
                throw BlueWhaleException.overStock();
            }
            // 锁定库存（减少可用、增加冻结）
            sp.setAmount(sp.getAmount() - cart.getQuantity());
            sp.setFrozen(sp.getFrozen() + cart.getQuantity());
            stockpileRepository.save(sp);

            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
            totalQuantity += cart.getQuantity();
        }

        // 3. 创建并保存订单
        Orders order = new Orders();
        order.setUserId(securityUtil.getCurrentUser().getId());
        order.setCartItemIds(cartItemIds);
        order.setShipping_address(shipping_address);
        order.setPaymentMethod(payment_method);
        order.setAmount(totalAmount);
        order.setQuantity(totalQuantity);
        // status 默认为 PENDING，createTime 由数据库自动设置
        order = ordersRepository.save(order);

        // 返回 VO
        return order.toVO();
    }

}
