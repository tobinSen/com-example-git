package com.example.spring.dynamic.aop.proxy;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class BarberTonyAfterAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        //do something
    }
}
