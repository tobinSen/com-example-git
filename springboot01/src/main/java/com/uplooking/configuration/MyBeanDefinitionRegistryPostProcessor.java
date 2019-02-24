package com.uplooking.configuration;

import com.uplooking.pojo.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {


    /**
     * 动态将对象注册到容器中
     * BeanDefinitionRegistry  Bean定义信息的保存中心，以后BeanFactory就是BeanDefinitionRegistry里面保存的每一个bean定义信息创建bean实例的
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Dog.class);
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Person.class).getBeanDefinition();
        //新增注册Bean的
        registry.registerBeanDefinition("Dog", beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        int count = factory.getBeanDefinitionCount();
        System.out.println("BeanFactory中定义的Bean" + count);
        factory.addBeanPostProcessor(new MyBeanPostProcessor());
    }
}
