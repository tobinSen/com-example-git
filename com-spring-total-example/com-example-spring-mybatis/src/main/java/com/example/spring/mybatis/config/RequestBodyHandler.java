package com.example.spring.mybatis.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Map;


//ResponseBodyAdvice
//RequestBodyAdvice
@ControllerAdvice
public class RequestBodyHandler implements ResponseBodyAdvice {


    //false 返回值，true拦截
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        Method method = methodParameter.getMethod();
        if (method == null) {
            return false;
        }
        return method.getReturnType().equals(Map.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
        System.out.println(body.toString());
        return body;
    }
}
