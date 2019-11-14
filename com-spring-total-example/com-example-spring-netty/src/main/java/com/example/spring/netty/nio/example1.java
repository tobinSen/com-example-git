package com.example.spring.netty.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 */
public class example1 {

    public static void main(String[] args) throws Exception {
        String str = "hello,nio,我是博学谷";
        FileOutputStream fos = new FileOutputStream("basic.txt");
        //通道
        FileChannel fc = fos.getChannel();
        //缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        //将缓冲区中的position置为0
        buffer.flip(); //idea中tab键切换方法
        //把缓冲区的数据写到通道里
        fc.write(buffer);
        fos.close();
    }

}
