package com.seecoder.TomatoMall.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;

import com.seecoder.TomatoMall.util.AliPay;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/alipay")
public class AliPayController {
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


    @GetMapping("/pay")
    public void pay(AliPay aliPay, HttpServletResponse httpResponse) throws Exception {
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId,
                privateKey, FORMAT, charset, alipayPublicKey, signType);
        // 2. 创建 Request并设置Request参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 发送请求的 Request类
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", aliPay.getTraceNo());  // 我们自己生成的订单编号
        bizContent.put("total_amount", aliPay.getTotalAmount()); // 订单的总金额
        bizContent.put("subject", aliPay.getSubject());   // 支付的名称
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");  // 固定配置
        request.setBizContent(bizContent.toString());
        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + charset);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @GetMapping("/return")
    public String returnUrl(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = AliPay.extractParams(request);
        boolean ok = AliPay.verifySignature(params, alipayPublicKey, charset, signType);
        if (ok) {
            // 支付成功，跳转到你购物车页面也可以，但用户看不出“支付结果”
            // 方式一：直接返回前端路由，假设你前端路由是 /cart
            return "redirect:http://localhost:5173/cart";
        } else {
            return "redirect:http://localhost:5173/cart?payResult=fail";
        }
    }

    // 支付宝服务器后台调用这里，做业务更新，一定要返回 "success"
    @PostMapping("/notify")
    public String notifyUrl(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = AliPay.extractParams(request);
        boolean ok = AliPay.verifySignature(params, alipayPublicKey, charset, signType);
        if (ok && "TRADE_SUCCESS".equals(params.get("trade_status"))) {
            String outTradeNo = params.get("out_trade_no");
            String totalAmount = params.get("total_amount");
            // TODO: 根据 outTradeNo 查订单 → 校验金额 → 更新订单状态
            return "success";  // 千万要返回 success
        }
        return "failure";
    }


}

