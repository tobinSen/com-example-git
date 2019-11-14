package com.example.spring.netty.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

public class NioDemo {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println("capacity：" + buffer.capacity()); //10

        for (int i = 0; i < 5; i++) {
            int num = new SecureRandom().nextInt(20);
            buffer.put(num);
        }
        System.out.println("before flip limit：" + buffer.limit());//10

        buffer.flip();

        System.out.println("after flip limit：" + buffer.limit()); //5

        System.out.println("enter while loop");

        while (buffer.hasRemaining()) {
            System.out.println("position：" + buffer.position()); //1 值的下一个索引
            System.out.println("limit：" + buffer.limit());      //5
            System.out.println("capacity：" + buffer.capacity()); //10
            System.out.println(buffer.get());
        }
    }
}
