package com.example.spring.layui.function;

/**
 * 如果要重写一个父类的方法，则可以使用匿名内部类的方式来进行重写操作
 */
@FunctionalInterface
public interface IntHandler {

    void say(); //函数式接口只能有一个抽象方法

    abstract class Innore {

        abstract void say();

        static void sayH() {
            System.out.println("sayH...");
        }

    }


    public static void main(String[] args) {
        IntHandler.Innore.sayH();
    }
}
