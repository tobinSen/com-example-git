package com.example.spring.web;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

@Component
public class MyWebRequestInterceptor implements WebRequestInterceptor {


    @Override
    public void preHandle(WebRequest request) throws Exception {
        System.out.println("preHandle...");
        System.out.println(request.getContextPath());
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        System.out.println("postHandle...");
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {

    }
}
