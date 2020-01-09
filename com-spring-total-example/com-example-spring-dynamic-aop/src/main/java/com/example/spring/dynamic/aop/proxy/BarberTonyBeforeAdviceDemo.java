package com.example.spring.dynamic.aop.proxy;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

public class BarberTonyBeforeAdviceDemo {

    //2点 target + advice
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        // 设置代理的目标类
        proxyFactory.setTarget(new BarberTony());
        // 为目标类添加增强
        proxyFactory.addAdvice(new BarberTonyBeforeAdvice());
        //proxyFactory.setOptimize(true); //优化代理这样针对接口的代理也会使用CglibAopProxy
        //proxyFactory.setInterfaces(BarberTonyBeforeAdviceDemo.class);//JdkDynamicAopProxy使用

        BarberTony barberTonyProxy = (BarberTony) proxyFactory.getProxy();
        barberTonyProxy.cut();
    }


    static class BarberTonyBeforeAdvice implements MethodBeforeAdvice {
        /**
         * 在此实现前置增强的逻辑
         *
         * @param method  目标类的方法
         * @param objects 目标类方法的入参
         * @param o       目标类实例
         * @throws Throwable 该方法抛出异常将会组织目标类的方法执行
         */
        public void before(Method method, Object[] objects, Object o) throws Throwable {
            // doSomething()
            System.out.println("methodBeforeAdvice");
        }
    }

    // 洗剪吹的Tony老师
    static class BarberTony {
        public void wash() {
            System.out.println("washing hair");
        }

        public void cut() {
            System.out.println("cutting hair");
        }

        public void dry() {
            System.out.println("drying hair");
        }
    }
}