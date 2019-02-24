package com.uplooking;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MyCondition implements Condition {

    /**
     * conditionContext :判断条件能使用的上下文(环境)
     * annotatedTypeMetadata：注解信息
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //获取到beanFactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        //获取到类加载器
        ClassLoader classLoader = context.getClassLoader();
        //当前环境信息
        Environment environment = context.getEnvironment();
        //Bean定义的注册类
        BeanDefinitionRegistry registry = context.getRegistry();
        String property = environment.getProperty("os.name");
        if (property.contains("windows")) {
            return true;
        }
        return false;

    }
}
