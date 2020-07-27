package com.example.spring.layui.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        System.err.println("MyBeanPostProcessor is constructor start ...");
    }

    @Override
    public Object postProcessBeforeInitialization(@Nullable Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization====>" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@Nullable Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization===>" + beanName);
        return bean;
    }
}
