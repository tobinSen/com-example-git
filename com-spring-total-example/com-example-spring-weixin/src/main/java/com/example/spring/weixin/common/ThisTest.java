package com.example.spring.weixin.common;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ThisTest {

    public void thisTest() {
        ThisTest test = this; //this就是调用者，在本类的对象，可以用来做单例传递
        ThisTest thisTest = new ThisTest();
        test.test();
        thisTest.test();
    }

    private void test() {
        System.out.println(new Random().nextInt());
    }
}
