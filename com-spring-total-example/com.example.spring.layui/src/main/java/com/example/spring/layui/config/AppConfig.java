package com.example.spring.layui.config;

import com.example.spring.layui.reflect.Animal;
import com.example.spring.layui.reflect.Cat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public String nameStr = "tonjain";

    public AppConfig() {
        System.err.println("AppConfig init constructor method");
    }

    @Bean
    public Animal animal() {
        return new Animal();
    }

    @Bean
    public Cat cat() {
        return new Cat();
    }

    /**
     * 问题是：spring要所有的后置处理器先进行初始化，以便后面的其他bean的生命周期中可以使用到这些后置处理器的方法?
     * <p>
     * 处理方法：@Bean  + static
     * 原理：静态是随类的加载而加载的，随类的消失而消失的，
     * <p>
     * <p>
     * postProcessBeanFactory(beanFactory)：先进行BeanPostProcessor注入到，在注入appConfig
     * <p>
     *
     *  BeanFactoryPostProcessor
     *  BeanPostProcessor
     */
    @Bean
    public static MyBeanPostProcessor myBeanPostProcessor() {
        return new MyBeanPostProcessor();
    }

    @Bean
    public static MyBeanFactoryPostProcessor myBeanFactoryPostProcessor() {
        return new MyBeanFactoryPostProcessor();
    }


}
