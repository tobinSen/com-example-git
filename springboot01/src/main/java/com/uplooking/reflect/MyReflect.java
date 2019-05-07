package com.uplooking.reflect;

import com.uplooking.pojo.Person;

import java.lang.reflect.*;

public class MyReflect {
    /**
     * 获取class对象三种方式：
     *  方式一：Class.forName("全类名")
     *  方式二：数据类型.class
     *  方式三：对象.getClass()
     *
     * 通过Class类获取类型的一些信息
     *
     * getName()类的名称（全名，全限定名）
     * getSimpleName()类的的简单名称（不带包名）
     * getModifiers(); 类的的修饰符
     * 创建对象 无参数构造创建对象 newInstance()
     * 获取指定参数的构造器对象，并可以使用Constructor对象创建一个实例 Constructor<T> getConstructor(Class<?>... parameterTypes)
     *
     *
     * class:
     *      getName():String
            getCanonicalName():String
            getSimpleName():String
            s+forName(String):Class<?>
            s+forName(String, boolean, ClassLoader):Class<?>

            判断类型：
                 isPrimitive():boolean
                 查看是否基本数据类型。
                 isArray():boolean
                 查看是否数组类型。
                 isInterface():boolean
                 查看是否接口类型。
                 isAnnotation():boolean
                 查看是否注解类型。
                 isEnum():boolean
                 查看是否枚举类型。
            组件类型：getComponentType():Class<?>
     获取内部类的
             getDeclaringClass():Class<?> ：获取成员内部类在定义时所在的类。
             getEnclosingClass():Class<?> ：获取内部类在定义时所在的类。
             getEnclosingConstructor():Constructor：获取局部或匿名内部类在定义时所在的构造器。
             getEnclosingMethod():Method：获取局部或匿名内部类在定义时所在的方法。

             isMemberClass():boolean：查看是否成员内部类。
             isLocalClass():boolean：查看是否局部内部类。
             isAnonymousClass():boolean：查看是否匿名内部类
     父子关系
         getSuperclass():Class<? super T>：获取继承的父类。
         getGenericSuperclass():Type ：获取父类和泛型参数
         getAnnotatedSuperclass():AnnotatedType ： 获取父类注解类型

         getInterfaces():Class<?>[]:获取实现的接口集。
         getGenericInterfaces():Type[]
         getAnnotatedInterfaces():AnnotatedType[]
     注意：ParameterizedType.getActualTypeArguments()：可以得到定义在类或者接口上的泛型类型

     转换类型：
         asSubclass(Class<U>):Class<? extends U> ：把该类型(子类)转换为目标类型(父类)。
         isAssignableFrom(Class<?>):boolean测试该类型(父类)是否为目标类型(子类)的父类

     成员内部类：
         getClasses():Class<?>[]
         getDeclaredClasses():Class<?>[]

     Type type = book.getGenericSuperclass();
     ParameterizedType parameterizedType = (ParameterizedType)type;
     Type[] arguments = parameterizedType.getActualTypeArguments();//泛型参数

     getActualTypeArguments()返回了一个Type数组,数组里是参数化类型的参数


     注解API:
     boolean isAnnotationPresent(Class<?extends Annotation> annotationClass)：判断是否是指定注解
     <T extends Annotation> T getAnnotation(Class<T> annotationClass)：返回指定注解包含父类
     Annotation[] getAnnotations()：返回所有注解
     <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass)：返回指定注解类型多个包含父类
     <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass)：获取指定注解只能是本类
     <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass)：获取指定类型只能是本类
     Annotation[] getDeclaredAnnotations()：获取本类所有的注解

     */

    public static void main(String[] args) throws Exception {
        Class<?> book = Class.forName("com.uplooking.reflect.Book");
        //book的全类名
        System.out.println(book.getName()); //全类名
        System.out.println(book.getSimpleName());//类名
        //default 0 public 1
        System.out.println(book.getModifiers()); //类型的修饰符

        Object o = book.newInstance();//产生一个无参构造器
        //Constructor<?> constructor = book.getConstructor(Long.class);//获取指定形参的构造器
        //Constructor<?> declaredConstructor = book.getDeclaredConstructor(Long.class);//获取本类中私有以上的构造器
        //Constructor<?>[] declaredConstructors = book.getDeclaredConstructors();//获取本类中所有私有以上的构造器

        Field id = book.getField("bookName");//获取指定的属性的Field对象
        Field[] fields = book.getFields();//获取所有公有属性的Field对象
        Field[] declaredFields = book.getDeclaredFields();//获取所有私有属性的Filed对象
        Field id1 = book.getDeclaredField("id");//获取指定私有的构造器

        Method[] methods = book.getMethods();//获取所有的公有方法
        //Method method = book.getMethod("convert", Long.class);//获取指定的方法Method
        Method[] declaredMethods = book.getDeclaredMethods();//获取所有私有的Method
        //Method covert = book.getDeclaredMethod("covert", Long.class);//获取指定私有方法
        Method enclosingMethod = book.getEnclosingMethod();//返回底的一个内

        //System.out.println(book.getTypeName());

        Class<?> componentType = book.getComponentType();
        System.out.println(componentType);
        AnnotatedType annotatedSuperclass = book.getAnnotatedSuperclass();
        System.out.println(annotatedSuperclass.getType().getTypeName());
        Class<? extends Person> subclass = book.asSubclass(Person.class);

        Type type = book.getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType)type;
        Type[] arguments = parameterizedType.getActualTypeArguments();//泛型参数

    }

}
