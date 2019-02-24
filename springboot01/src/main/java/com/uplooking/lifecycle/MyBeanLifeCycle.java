package com.uplooking.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class MyBeanLifeCycle implements InitializingBean, DisposableBean, BeanPostProcessor {


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return null;
    }

    @PostConstruct
    public void init(){
        System.out.println("init...");
    }

    @PreDestroy
    public void MyDestroy(){
        System.out.println("destroy");
    }

    /**
     * 容器管理Bean的生命周期
     *
     * 构造方法（对象创建）
     *      单实例：在容器启动的时候创建对象
     *      多实例：在每次获取的时候创建对象
     *   初始化：
     *          对象创建完成，并赋值好，调用初始化方法
     *    销毁：
     *          单实例：容器关闭时候
     *          多实例：容器不会管理这个Bean,容器不会调用destroy-method方法
     *
     *  1.指定初始化和销毁方法：
     *      通过@Bean主机指定 init-method 和 destroy-method
     *  2.通过Bean实现InitializingBean,DisposableBean
     *  3.可以是使用JSR250
     *          @PostConstruct : Bean创建后调用
     *          @PreDestroy ： Bean销毁之前调用
     *  4.BeanPostProcessor:Bean的后处理Bean(添加容器中，每个bean初始化都会调用)
     *         原理：1.先给bean的属性赋值
     *              2.before方法
     *              3.bean自定义初始化
     *              4.after
     *
     */
}
