package com.example.spring.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        if (socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("。。。");
            }
        }

        //将数组转换为一个byteBuffer
        ByteBuffer byteBuffer = ByteBuffer.wrap("tongj".getBytes());
        socketChannel.write(byteBuffer);

        System.in.read();//暂停

    }
}
