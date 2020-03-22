package com.example.spring.hutool.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

//注解 + 构造方法的问题
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private String[] annotatedPackage;

    private String[] ignoreAnnotatedPackages;

    public MyBeanDefinitionRegistryPostProcessor(String[] annotatedPackage, String... ignoreAnnotatedPackages) {
        this.annotatedPackage = annotatedPackage;
        this.ignoreAnnotatedPackages = ignoreAnnotatedPackages;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.addBeanPostProcessor(null);//这里可以动态的注册后置处理器bean
    }
}
