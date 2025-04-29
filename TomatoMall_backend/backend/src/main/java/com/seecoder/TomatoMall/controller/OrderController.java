// OrderController.java
package com.seecoder.TomatoMall.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.seecoder.TomatoMall.exception.BlueWhaleException;
import com.seecoder.TomatoMall.po.Orders;
import com.seecoder.TomatoMall.repository.OrdersRepository;
import com.seecoder.TomatoMall.vo.AliForm;
import com.seecoder.TomatoMall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
//    @Value("${alipay.notify-url}")
//    private String notifyUrl;
//    @Value("${alipay.return-url}")
//    private String returnUrl;

    @Autowired
    private OrdersRepository ordersRepository;

    @PostMapping("/{orderId}/pay")
    public ResultVO<AliForm> payOrder(@PathVariable("orderId") Integer orderId) throws AlipayApiException {

        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> BlueWhaleException.orderNotFound());


        AlipayClient alipayClient = new DefaultAlipayClient(
                serverUrl, appId, privateKey, FORMAT, charset, alipayPublicKey, signType
        );


        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
//        request.setReturnUrl(returnUrl);
//        request.setNotifyUrl(notifyUrl);

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

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public ResultVO<String> payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }
            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, alipayPublicKey, "UTF-8"); // 验证签名

            if (checkSignature) {

                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
            }
        }
        return ResultVO.buildSuccess("支付成功");
    }

}