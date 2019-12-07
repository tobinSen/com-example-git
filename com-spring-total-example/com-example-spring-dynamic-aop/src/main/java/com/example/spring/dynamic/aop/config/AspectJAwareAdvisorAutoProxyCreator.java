package com.example.spring.dynamic.aop.config;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

    private AbstractBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //1.判断bean类型，排除自身
        if (bean instanceof AspectJExpressionPointcutAdvisor) {
            return bean;
        }
        if (bean instanceof MethodBeforeAdvice) {
            return bean;
        }
        //2.获取切面
        AspectJExpressionPointcutAdvisor advisor = beanFactory.getBean(AspectJExpressionPointcutAdvisor.class);
        //3.切点匹配
        if (advisor.getPointcut().getClassFilter().matches(bean.getClass())) {

            ProxyFactory proxyFactory = new ProxyFactory();
            //4.设置通知
            proxyFactory.addAdvice(advisor.getAdvice());
            //5.设置目标类
            proxyFactory.setTarget(bean);
            return proxyFactory.getProxy();
        }
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (AbstractBeanFactory) beanFactory;
    }

}
