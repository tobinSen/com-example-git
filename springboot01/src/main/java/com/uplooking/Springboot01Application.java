package com.uplooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
/**
 * AOP的原理：
 *  1、@EnableAspectJAutoProxy 开启AOP功能
 *  2、@EnableAspectJAutoProxy 会给容器中注册一个组件AnnotationAwareAspectJAutoProxyCreator
 *  3、AnnotationAwareAspectJAutoProxyCreator是一个后置处理器==》BeanPostProcess
 *  4、容器创建流程；
 *      1、registerBeanPostProcessors()注册后置处理器，创建AnnotationAwareAspectJAutoProxyCreator
        2、finishBeanFactoryInitialization()初始化剩下的单实例bean
            1、创建业务逻辑组件和切面组件
            2、AnnotationAwareAspectJAutoProxyCreator拦截组件的创建过程
            3、组件创建完之后，判断组件是否需要增强
            4、是切面的通知方法，包装增强器（Advisor）给业务逻辑组件创建一个代理对象（cglib）
    5、执行目标方法：
        1、代理对象执行目标方法
        2、CglibAopProxy.intercept()
            1、得到目标方法的拦截器链(增强包装成拦截器MethodInterceptor)
            2、利用拦截器链机制，依次进入每一个拦截器进行执行
            3、效果：
                正常执行：前置通知-》目标方法-》后置通知-》返回通知
                异常执行：前置通知-》目标方法-》后置通知-》异常通知
 */
public class Springboot01Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Springboot01Application.class);
        application.run(args);
    }
}
