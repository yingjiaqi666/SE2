package com.seecoder.BlueWhale.configure;

import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: DingXiaoyu
 * @Date: 0:17 2023/11/26
 * 这个类定制了一个登录的拦截器，
 * SpringBoot的拦截器标准为HandlerInterceptor接口，
 * 这个类实现了这个接口，表示是SpringBoot标准下的，
 * 在preHandle方法中，通过获取请求头Header中的token，
 * 判断了token是否合法，如果不合法则抛异常，
 * 合法则将用户信息存储到request的session中。
*/
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if (token != null && tokenUtil.verifyToken(token)) {
            request.getSession().setAttribute("currentUser",tokenUtil.getUser(token));
            return true;
        }else {
            throw BlueWhaleException.notLogin();
        }
    }

}
