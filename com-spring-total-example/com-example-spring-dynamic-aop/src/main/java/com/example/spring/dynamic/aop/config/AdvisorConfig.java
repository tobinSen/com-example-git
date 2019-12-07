package com.example.spring.dynamic.aop.config;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class AdvisorConfig {

    @Value("${expression}")
    private String expression;


    @Bean
    public AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor(MethodBeforeAdvice methodBeforeAdvice) {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setAdvice(methodBeforeAdvice);
        advisor.setExpression(expression);
        return advisor;
    }

    @Bean
    public MethodBeforeAdvice methodBeforeAdvice() {
        return new MyMethodBeforeAdvice();
    }


    class MyMethodBeforeAdvice implements MethodBeforeAdvice {

        @Override
        public void before(Method method, Object[] objects, Object o) throws Throwable {
            System.out.println("前置通知");
        }
    }
}
