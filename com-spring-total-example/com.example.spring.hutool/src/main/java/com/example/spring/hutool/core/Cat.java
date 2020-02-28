package com.example.spring.hutool.core;

import cn.hutool.core.clone.CloneRuntimeException;
import cn.hutool.core.clone.CloneSupport;
import cn.hutool.core.clone.Cloneable;
import cn.hutool.core.util.ObjectUtil;

/**
 * 猫猫类，使用实现Cloneable方式
 *
 * @author Looly
 */
public class Cat implements Cloneable<Cat> {

    private String name = "miaomiao";
    private int age = 2;

    @Override
    public Cat clone() {
        try {
            return (Cat) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneRuntimeException(e);
        }
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
    }
}
