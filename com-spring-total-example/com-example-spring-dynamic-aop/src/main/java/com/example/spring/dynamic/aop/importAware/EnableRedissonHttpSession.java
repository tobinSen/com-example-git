package com.example.spring.dynamic.aop.importAware;


import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({RedissonHttpSessionConfiguration.class})
public @interface EnableRedissonHttpSession {

    AdviceMode mode() default AdviceMode.PROXY; //必须含有

    int maxInactiveIntervalInSeconds() default 1800;

    String keyPrefix() default "";

}
