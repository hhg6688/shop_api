package com.fh.common.Interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KuaYuInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        String originalURL = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", originalURL);
        String method = request.getMethod();
        if(method.equalsIgnoreCase("options")){
            response.setHeader("Access-Control-Allow-Headers", "token");
            return false;
        }
        return true;
    }
}
