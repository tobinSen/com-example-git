package com.example.spring.netty.zeroCopy;


/**
 * 零拷贝：
 * <p>
 * 用户空间                   内核空间                    磁盘
 * <p>
 * TCP的粘包和拆包（TCP是个“流”协议，没有界限的一串数据）
 * 一个完整的包可能会被TCP拆分成多个包进行发送，也有可能把多个小的包封装成一个大的数据包发送
 * <p>
 * netty中的解决方式：使用定长的消息长度 23349
 */
public class zeroCopyDemo {

    public static void main(String[] args) throws InterruptedException {

        //转为16进制
        String format = String.format("%x", 44);
        System.out.println(format);
        while (true) {
            Thread.sleep(1000);
        }

    }

}
