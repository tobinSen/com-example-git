package com.example.spring.dynamic.aop.proxy;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * 可以在同一个异常抛出增强中定义多个afterThrowing方法，抛出异常时Spring会自动选择匹配度最高的方法
 */
public class BarBerTonyThrowsAdvice implements ThrowsAdvice {

    // 入参必须是 Throwable 及其子类
    public void afterThrowing(Exception ex) {
    }

    // 前三个入参 Method method, Object[] args, Object target 要么都提供，要么都不提供
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
    }
}
