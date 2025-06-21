// OrderController.java
package com.seecoder.TomatoMall.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.seecoder.TomatoMall.exception.TomatoMallException;
import com.seecoder.TomatoMall.po.Orders;
import com.seecoder.TomatoMall.po.Stockpile;
import com.seecoder.TomatoMall.repository.CartRepository;
import com.seecoder.TomatoMall.repository.OrdersRepository;
import com.seecoder.TomatoMall.repository.StockpileRepository;
import com.seecoder.TomatoMall.util.AliPay;
import com.seecoder.TomatoMall.vo.AliForm;
import com.seecoder.TomatoMall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Value("${alipay.app-id}")
    private String appId;
    @Value("${alipay.private-key}")
    private String privateKey;
    @Value("${alipay.alipay-public-key}")
    private String alipayPublicKey;
    @Value("${alipay.server-url}")
    private String serverUrl;
    @Value("${alipay.charset}")
    private String charset;
    @Value("${alipay.sign-type}")
    private String signType;
    @Value("${alipay.notify-url}")
    private String notifyUrl;
    @Value("${alipay.return-url}")
    private String returnUrl;
    private static final String FORMAT = "JSON";

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private StockpileRepository stockpileRepository;

    @Autowired
    private CartRepository cartRepository;



    @PostMapping("/{orderId}/pay")
    public ResultVO<AliForm> payOrder(@PathVariable("orderId") Integer orderId) throws AlipayApiException {

        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> TomatoMallException.orderNotFound());


        AlipayClient alipayClient = new DefaultAlipayClient(
                serverUrl, appId, privateKey, FORMAT, charset, alipayPublicKey, signType
        );


        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(returnUrl);
        request.setNotifyUrl(notifyUrl);

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", order.getOrderId().toString());
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        bizContent.put("total_amount", order.getAmount().toString());
        bizContent.put("subject", "商品订单支付");
        request.setBizContent(bizContent.toString());

        // 4. 执行并获取表单
        String form = alipayClient.pageExecute(request).getBody();

        // 5. 封装返回
        AliForm aliForm = new AliForm();
        aliForm.setPaymentForm(form);
        aliForm.setOrderId(order.getOrderId().toString());
        aliForm.setTotalAmount(order.getAmount());
        aliForm.setPaymentMethod(order.getPaymentMethod());

        return ResultVO.buildSuccess(aliForm);
    }

    @GetMapping("/return")
    public String returnUrl(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = AliPay.extractParams(request);
        boolean ok = AliPay.verifySignature(params, alipayPublicKey, charset, signType);
        if (ok) {
            return "redirect:http://127.0.0.1:5173/cart";
        } else {
            return "redirect:http://127.0.0.1:5173/cart?payResult=fail";
        }
    }

    // 支付宝服务器后台调用这里，做业务更新，一定要返回 "success"
    @PostMapping("/notify")
    public String notifyUrl(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = AliPay.extractParams(request);
        boolean ok = AliPay.verifySignature(params, alipayPublicKey, charset, signType);
        if (ok && "TRADE_SUCCESS".equals(params.get("trade_status"))) {
            String outTradeNo = params.get("out_trade_no");
            Orders order = ordersRepository.findByOrderId(Integer.valueOf(outTradeNo))
                    .orElseThrow(() -> TomatoMallException.orderNotFound());

            // 根据 outTradeNo 查订单 → 更新订单状态

            order.setStatus("PAID");
            ordersRepository.save(order);

            List<String> cartItemIds = order.getCartItemIds();
            for (String cartIdStr : cartItemIds) {
                int productId = cartRepository.findByCartItemId(Integer.valueOf(cartIdStr))
                        .getProductId();
                Stockpile sp = stockpileRepository.findByProductid(String.valueOf(productId));
                int locked = cartRepository.findByCartItemId(Integer.valueOf(cartIdStr)).getQuantity();
                sp.setFrozen(sp.getFrozen() - locked);
                stockpileRepository.save(sp);

                return "success";
            }
        }
        return "failure";
    }

}