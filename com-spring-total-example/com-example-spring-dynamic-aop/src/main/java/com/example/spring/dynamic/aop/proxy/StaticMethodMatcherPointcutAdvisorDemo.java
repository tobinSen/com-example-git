package com.example.spring.dynamic.aop.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

public class StaticMethodMatcherPointcutAdvisorDemo {

    //3点 target + advisor(advice)
    public static void main(String[] args) throws Exception {
        ProxyFactory proxyFactory = new ProxyFactory();
        // 设置目标类
        proxyFactory.setTarget(new Waitress());

        // 在切面上添加增强
        WaitressAdvisor waitressAdvisor = new WaitressAdvisor();
        waitressAdvisor.setAdvice(new WaitressAdvice());

        // 增加切面
        proxyFactory.addAdvisor(waitressAdvisor);

        Waitress waitress = (Waitress) proxyFactory.getProxy();
        waitress.sayHello("zzx");
        waitress.order("cola");
    }


    /**
     * 目标类
     */
    static class Waitress {
        public void sayHello(String name) {
            System.out.println("hello " + name + "!");
        }

        public void order(String food) {
            System.out.println("order " + food + "!");
        }
    }

    /**
     * 定义切面
     * StaticMethodMatcherPointcutAdvisor唯一需要定义的是matches()方法
     * 默认匹配所有类，可以通过getClassFilter()方法让它仅匹配指定类
     */
    static class WaitressAdvisor extends StaticMethodMatcherPointcutAdvisor {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return "sayHello".equals(method.getName());
        }
    }

    /**
     * 定义增强，此处定义了一个方法前置增强
     */
    static class WaitressAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.err.println("before");
            Object proceed = invocation.proceed();
            System.err.println("after");
            return proceed;

        }
    }
}