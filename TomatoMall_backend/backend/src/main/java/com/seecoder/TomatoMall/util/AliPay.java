package com.seecoder.TomatoMall.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Data
public class AliPay {
    private String traceNo;// 我们自己生成的订单编号
    private double totalAmount;// 订单的总金额
    private String subject;// 支付的名称
    private String alipayTraceNo;

    public static Map<String,String> extractParams(HttpServletRequest request) {
        Map<String,String> params = new HashMap<>();
        // request.getParameterMap() 返回 Map<String, String[]>
        request.getParameterMap().forEach((key, values) -> {
            if (values != null && values.length > 0) {
                params.put(key, values[0]);
            }
        });
        return params;
    }

    public static boolean verifySignature(Map<String,String> params,
                                          String alipayPublicKey,
                                          String charset,
                                          String signType) throws AlipayApiException {

        return AlipaySignature.rsaCheckV1(
                params,
                alipayPublicKey,
                charset,
                signType
        );
    }
}