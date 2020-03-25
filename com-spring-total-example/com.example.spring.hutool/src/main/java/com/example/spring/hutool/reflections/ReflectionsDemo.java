package com.example.spring.hutool.reflections;

import com.example.spring.hutool.core.Cat;
import com.google.common.base.Stopwatch;
import org.apache.naming.factory.BeanFactory;
import org.junit.jupiter.api.Test;
import org.reflections.ReflectionUtils;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.regex.Pattern;

public class ReflectionsDemo {

    /**
     * @author micocube
     * projectName: utils4j
     * packageName: com.coding.reflect
     * email: ldscube@gmail.com
     * createTime: 2019-07-05 16:20
     * version: 0.1
     * description: reflection 框架
     * Reflection依赖了谷歌的Guava包，出现此问题可能是因为缺少Guava或者包冲突。
     * Reflections 0.9.11用的是Guava 20，Reflection 0.9.10用的是Guava 18，
     * 如果需要用Guava 18，则只能用Reflection 0.9.10版本。
     * <dependency>
     * <groupId>org.reflections</groupId>
     * <artifactId>reflections</artifactId>
     * <version>0.9.11</version>
     * </dependency>
     * <p>
     * <p>
     * 1.获取某个类型的全部子类
     * 2.只要类型、构造器、方法，字段上带有特定注解，便能获取带有这个注解的全部信息（类型、构造器、方法，字段）
     * 3.获取所有能匹配某个正则表达式的资源
     * 4.获取所有带有特定签名的方法，包括参数，参数注解，返回类型
     * 5.获取所有方法的名字
     * 6.获取代码里所有字段、方法名、构造器的使用
     */


    //获取所有Inputstream的子类，限定只扫包前缀为“java”的，也就是jdk自带的
    @Test
    public void testPackage() {
        org.reflections.Reflections reflections = new org.reflections.Reflections("java.");
        Stopwatch stopwatch = Stopwatch.createStarted();
        Set<Class<? extends InputStream>> allTypes = reflections.getSubTypesOf(InputStream.class);
        System.out.println(stopwatch.toString());
        for (Class type : allTypes) {
            System.out.println("Found:" + type.getName());
        }
    }


    // com.coding 包下的所有带有service注解的类
    @Test
    public void testAnnotationType() {
        org.reflections.Reflections reflections = new org.reflections.Reflections("com.coding");
        Stopwatch stopwatch = Stopwatch.createStarted();
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Service.class);
        System.out.println(stopwatch.toString());
        for (Class type : classes) {
            System.out.println("Found:" + type.getName());
        }
    }


    // 假如想扫描整个工程的类，直接new一个不带参数的Reflections就好。
    // 值得一提的是，这东西在扫描的时候，连依赖的jar包都不放过。以Spring框架的BeanFactory为例：
    @Test
    public void testSubType2() {
        org.reflections.Reflections reflections = new org.reflections.Reflections();
        Set<Class<? extends BeanFactory>> classes = reflections.getSubTypesOf(BeanFactory.class);

        for (Class clazz : classes) {
            //logger.info(clazz.getName());
            System.out.println("Found: " + clazz.getName());
        }
    }


    // 所有带RequestMapping注解的方法
    @Test
    public void testAnnotationMethod() {
        org.reflections.Reflections reflections = new org.reflections.Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage("com.coding"))
                        .addScanners(new MethodAnnotationsScanner()));

        Set<Method> annotatedWith = reflections.getMethodsAnnotatedWith(RequestMapping.class);

        for (Method p : annotatedWith) {
            //logger.info(clazz.getName());
            System.out.println("Found: " + p.getName());
        }
    }

    // 所有properties文件
    @Test
    public void testResource() {
        org.reflections.Reflections reflections = new org.reflections.Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage("com.coding"))
                        .addScanners(new ResourcesScanner()));

        Set<String> properties =
                reflections.getResources(Pattern.compile(".*\\.properties"));

        for (String p : properties) {
            //logger.info(clazz.getName());
            System.out.println("Found: " + p);
        }
    }

    // 所有带Deprecated注解的构造方法
    @Test
    public void testAnnotationConstractor() {
        org.reflections.Reflections reflections = new org.reflections.Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage("com.coding"))
                        .addScanners(new MethodAnnotationsScanner()));

        Set<Constructor> annotated = reflections.getConstructorsAnnotatedWith(Deprecated.class);
        for (Constructor p : annotated) {
            //logger.info(clazz.getName());
            System.out.println("Found: " + p.getName());
        }
    }

    // 所有带Id注解的构造方法
    /*@Test
    public void testAnnotationField() {
        org.reflections.Reflections reflections = new org.reflections.Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage("com.coding"))
                        .addScanners(new FieldAnnotationsScanner()));

        Set<Field> annotated = reflections.getFieldsAnnotatedWith(Id.class);
        for (Field p : annotated) {
            //logger.info(clazz.getName());
            System.out.println("Found: " + p.getName());
        }
    }*/

    // MethodParameterScanner 方法参数扫描器
    @Test
    public void testMethodParameter() {
        org.reflections.Reflections reflections = new org.reflections.Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage("com.coding"))
                        .addScanners(new MethodParameterScanner()));

        //MethodParameterScanner
        Set<Method> someMethods =
                reflections.getMethodsMatchParams(String[].class);
        Set<Method> voidMethods =
                reflections.getMethodsReturn(Cat.class);
        Set<Method> pathParamMethods =
                reflections.getMethodsWithAnyParamAnnotated(PathVariable.class);


        for (Method p : someMethods) {
            //logger.info(clazz.getName());
            System.out.println("方法参数符合String[]的class：Found: " + p.getName());
        }

        for (Method p : voidMethods) {
            //logger.info(clazz.getName());
            System.out.println("返回值为User的方法：Found: " + p.getName());
        }
        for (Method p : pathParamMethods) {
            //logger.info(clazz.getName());
            System.out.println("参数带有PathVariable注解的方法：Found: " + p.getName());
        }

        Set<Method> methods = ReflectionUtils.getAllMethods(Cat.class, ReflectionUtils.withModifier(Modifier.PUBLIC), ReflectionUtils.withReturnType(Boolean.class));
    }


    // 测试框架自带的工具类
    /*@Test
    public void testReflectionUtils() {
        // 所有get方法
        Set<Method> getters = org.reflections.ReflectionUtils.getAllMethods(Cat.class,
                org.reflections.ReflectionUtils.withModifier(Modifier.PUBLIC),
                org.reflections.ReflectionUtils.withPrefix("get"),
                org.reflections.ReflectionUtils.withParametersCount(0));

        // 参数是Collection的子类，返回值是boolean
        Set<Method> listMethodsFromCollectionToBoolean =
                org.reflections.ReflectionUtils.getAllMethods(List.class,
                        org.reflections.ReflectionUtils.withParametersAssignableTo(Collection.class),
                        org.reflections.ReflectionUtils.withReturnType(boolean.class));

        // field 带有Column注解，类型是String的子类
        Set<Field> fields = org.reflections.ReflectionUtils.getAllFields(Docker.class,
                org.reflections.ReflectionUtils.withAnnotation(Column.class),
                org.reflections.ReflectionUtils.withTypeAssignableTo(String.class));


        getters.forEach(get -> System.out.println("getter:" + get));
        listMethodsFromCollectionToBoolean.forEach(get -> System.out.println("listMethodsFromCollectionToBoolean:" + get));
        fields.forEach(get -> System.out.println("fields:" + get));
    }*/

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface id {

    }


}
