package com.example.spring.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static void main1(String[] args) throws Exception {
        //创建serverSocketChannel

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        Selector selector = Selector.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(7777));

        serverSocketChannel.configureBlocking(false);//非阻塞模式

        //把serverSocketChannel注册到selector ops = ops_accept
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }

            //获取到selectionKey,已经基于事件驱动的事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                //链接事件
                if (selectionKey.isAcceptable()) {
                    //生成一个socketChannel 分配一个socketChannel,这里是激活的客户端的socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    //将socketChannel注册到selector ,关注事件为OP_READ
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(2024));
                }

                if (selectionKey.isReadable()) {
                    //通过key反向获取对应的channel
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    //获取到channel获取到的buffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    socketChannel.read(byteBuffer);
                    System.out.println("客户端:" + new String(byteBuffer.array()));
                }

                //手动删除selectorKey
                iterator.remove();
            }

        }

    }

    public static void main(String[] args) {
        System.out.println(1 << 0);
        System.out.println(1 << 1);
        System.out.println(1 << 2);
        System.out.println(1 << 3);

        System.out.println("========");
        System.out.println(2 << 0);
        System.out.println(2 << 1);
        System.out.println(2 << 2);
        System.out.println(2 << 3);
    }
}
