package com.uplooking.importselector;

import com.uplooking.pojo.Person;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     * @param annotationMetadata     : 当前类的注解信息
     * @param beanDefinitionRegistry :BeanDefinition注册类
     *                               把所有需要添加到容器中的bean
     */

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        boolean dogDefinition = registry.containsBeanDefinition("dog");
        if (dogDefinition) {
            //bean的注册信息
            BeanDefinition beanDefinition = new RootBeanDefinition(Person.class);
            //指定Bean的Id
            registry.registerBeanDefinition("person", beanDefinition);
        }
    }
}
