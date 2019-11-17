package com.example.spring.netty.zeroCopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {

    public static void main(String[] args) throws Exception {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);

        String fileName = "/User/Desktop/test.zip";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        fileChannel.transferTo(0, fileChannel.size(), socketChannel);

    }
}
