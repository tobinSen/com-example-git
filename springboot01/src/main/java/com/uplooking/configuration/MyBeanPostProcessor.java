package com.uplooking.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public class MyBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("后处理BeanInit");
        if (o instanceof PropertySourcesPlaceholderConfigurer) {
            System.out.println(o);
        }
        System.out.println(s);
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("后处理BeanAfter");
        System.out.println(o.toString());
        System.out.println(s);
        return null;
    }
}
