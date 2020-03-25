package com.example.spring.hutool.core;

import cn.hutool.core.clone.CloneRuntimeException;
import cn.hutool.core.clone.CloneSupport;
import cn.hutool.core.clone.Cloneable;
import cn.hutool.core.util.ObjectUtil;

import java.lang.reflect.Array;
import java.util.Date;

/**
 * 猫猫类，使用实现Cloneable方式
 *
 * @author Looly
 */
public class Cat implements Cloneable<Cat> {

    private String name;
    private int age = 2;
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public Cat clone() {
        try {
            return (Cat) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneRuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                '}';
    }

    private static class Dog extends CloneSupport<Dog> {
        private String name = "wangwang";
        private int age = 3;
    }

    public static void main(String[] args) {
        Cat cat = new Cat();
        Cat clone = cat.clone();
        System.out.println(cat == clone);

        ObjectUtil.cloneByStream(cat); //深度拷贝
        Class<Cat> c = Cat.class;
        System.out.println(Cat.class == c);
        //Cat cast = c.cast(c); //这里是将内部对象转为这个对象（内强转为外）
        //Class<? extends Cloneable> aClass = c.asSubclass(Cloneable.class);//外强转为内
        Object o = Array.newInstance(String.class, 2);
        System.out.println(o.toString());
        System.out.println(o.getClass().isAssignableFrom(Array.class));

    }
}
