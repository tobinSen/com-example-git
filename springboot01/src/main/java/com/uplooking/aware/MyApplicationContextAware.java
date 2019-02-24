package com.uplooking.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringValueResolver;

public class MyApplicationContextAware implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

    //Aware===》AwareProcessor

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String beanName) {
        System.out.println("当前beanId:"+beanName);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        //字符串解析
        String s = resolver.resolveStringValue("${os.name}");
    }
}
