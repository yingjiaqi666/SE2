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
import com.seecoder.BlueWhale.vo.CheckoutRequest;
import com.seecoder.BlueWhale.vo.OrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
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

    @Override
    public boolean delete(String cartItemId){
        if(cartRepository.findByCartItemId(Integer.parseInt(cartItemId))==null)
            throw BlueWhaleException.productNotInCart();
        cartRepository.deleteCartByCartItemId(Integer.parseInt(cartItemId));
        return true;
    }

    @Override
    public boolean update(String cartItemId, Integer quantity){
        Cart cart = cartRepository.findByCartItemId(Integer.parseInt(cartItemId));
        if(cart==null)
            throw BlueWhaleException.productNotInCart();
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


    @Override
    public OrdersVO commitOrder(CheckoutRequest req) {
        List<String> cartItemIds = req.getCartItemIds();
        CheckoutRequest.ShippingAddress shipping_address = req.getShipping_address();
        String payment_method = req.getPayment_method();
        List<Integer> ids = cartItemIds.stream().map(Integer::valueOf).collect(Collectors.toList());
        List<Cart> carts = cartRepository.findAllById(ids);
        if (carts.size() != ids.size()) {
            throw BlueWhaleException.productNotInCart();
        }


        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalQuantity = 0;
        for (Cart cart : carts) {
            Stockpile sp = stockpileRepository.findByProductid(String.valueOf(cart.getProductId()));
            Product product = productRepository.findById(Integer.parseInt(String.valueOf(cart.getProductId())));
            if (sp.getAmount() < cart.getQuantity()) {
                throw BlueWhaleException.overStock();
            }
            // 锁定库存
            sp.setAmount(sp.getAmount() - cart.getQuantity());
            sp.setFrozen(sp.getFrozen() + cart.getQuantity());
            stockpileRepository.save(sp);

            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
            totalQuantity += cart.getQuantity();
        }

        Orders order = new Orders();
        order.setUserId(securityUtil.getCurrentUser().getId());
        order.setCartItemIds(cartItemIds);
        order.setShipping_address(shipping_address.getAddress());
        order.setPaymentMethod(payment_method);
        order.setAmount(totalAmount);
        order.setQuantity(totalQuantity);
        order = ordersRepository.save(order);

        OrdersVO temp = order.toVO();
        temp.setUsername(securityUtil.getCurrentUser().getUsername());

        // 返回 VO
        return temp;
    }

}
