package com.example.spring.dynamic.aop.importAware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

@Configuration //这个注解是必须的，@Import + ImportAware + @Configuration
public class RedissonHttpSessionConfigurationAware implements ImportAware, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setImportMetadata(AnnotationMetadata metadata) {
        String applicationName = applicationContext.getApplicationName();
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(EnableRedissonHttpSession.class.getName(), false);
        AnnotationAttributes map = AnnotationAttributes.fromMap(annotationAttributes);
        System.out.println(map);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
