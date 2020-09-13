package com.example.spring.layui.cli;

//内部类导包的问题

import com.example.spring.layui.Inner.Outer.Inner;

public class DemoMain {

    public static void main(String[] args) {
        //成员内部类创建方式 public
        //Outer.Inner i = new Outer().new Inner();

        //public static
        Inner inner = new Inner();

    }
}
