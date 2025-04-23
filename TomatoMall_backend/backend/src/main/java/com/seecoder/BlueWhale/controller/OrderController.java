// OrderController.java
package com.seecoder.BlueWhale.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.Orders;
import com.seecoder.BlueWhale.repository.OrdersRepository;
import com.seecoder.BlueWhale.vo.AliForm;
import com.seecoder.BlueWhale.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private static final String FORMAT = "JSON";

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

    @Autowired
    private OrdersRepository ordersRepository;

    @PostMapping("/{orderId}/pay")
    public ResultVO<AliForm> payOrder(@PathVariable("orderId") Integer orderId) throws AlipayApiException {
        // 1. 查询订单
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> BlueWhaleException.orderNotFound());

        // 2. 构建 AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
                serverUrl, appId, privateKey, FORMAT, charset, alipayPublicKey, signType
        );

        // 3. 创建支付请求
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

    // 回调处理方法保持不变
}