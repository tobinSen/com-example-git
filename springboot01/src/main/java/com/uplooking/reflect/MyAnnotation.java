package com.uplooking.reflect;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface MyAnnotation {

    String value() default StringUtils.EMPTY;

    boolean enable() default false;
}
