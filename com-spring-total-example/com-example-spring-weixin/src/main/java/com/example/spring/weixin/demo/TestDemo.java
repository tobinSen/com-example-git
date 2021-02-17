package com.example.spring.weixin.demo;

public class TestDemo {
    public static void main(String[] args) {



        B b = null;
        {
            A a = new A();
            b = a.getB(); // 引用指向改变了
        } //离开这个花括号，a消亡，a的属性b也不复存在（注意b是变量），但是main的b还是存在
        //main的b指向a的b所指向的对象，所以在A的构造方法中new出来的b对象依然没有消亡
        //a对象有自己的内存空间，b对象有自己的内存空间
        //a的b属性只是一个指针，它指向b对象，即a的b属性的变量本身的内存保存b对象的地址
        //所以a消亡只是a的b指针变量不复存在，并不是b对象不存在，只要还有其他变量引用b的话

        System.out.println(b);

    }


    static class A {
        B b;

        public A() {
            b = new B();
        }

        public B getB() {
            return b;
        }
    }

    static class B {

    }
}
