package com.uplooking.nio;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

public class MySelectableChannel {

    @SneakyThrows
    public static void main(String[] args) {
        //客户端
        //1、获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
        //2、切换非阻塞模式
        sChannel.configureBlocking(false);
        //3、指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        //4、发送时间给服务端
        buf.put(new Date().toString().getBytes());
        buf.flip();
        sChannel.write(buf);
        buf.clear();
        //5、关闭通道
        sChannel.close();

        //服务端
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        //将通道注册到选择器上，并且指定监听的事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //轮询获取选择器上面的事件
        while (selector.select() > 0) {
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                if (key.isAcceptable()) {
                    //接收就绪，获取客户端连接
                    SocketChannel channel = serverSocketChannel.accept();
                    //切换非阻塞模式
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();

                }

                //取消选择器
                it.remove();
            }
        }
    }
}
