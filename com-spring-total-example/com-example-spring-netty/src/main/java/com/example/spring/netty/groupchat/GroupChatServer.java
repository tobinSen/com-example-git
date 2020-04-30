package com.example.spring.netty.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //监听
    private void listen() {
        try {

            while (true) {
                int count = selector.select();//判断是否有事件发生,发生了事件就会>0
                if (count > 0) {//有事件发生
                    // 1.获取已经触发事件的key
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 2.链接事件
                        if (key.isAcceptable()) {
                            // 3.获取链接的客户端
                            SocketChannel sc = serverSocketChannel.accept();
                            // 4.注册链接的客户端(注册的事件为只读事件)
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + "：上线了！");
                        }

                        if (key.isReadable()) {
                            //处理读
                            readData(key);
                        }

                        //防止重复处理，标记这个selectKey已经处理了
                        iterator.remove();
                    }

                } else {
                    System.out.println("等待，无事件发生");
                }
            }
        } catch (Exception ignore) {

        } finally {

        }
    }

    private void readData(SelectionKey key) throws IOException {
        SocketChannel channel = null;
        //获取关联的channel

        try {
            // 1.获取到上线的客户端
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 2.将通道中的数据写入到缓冲区中
            int count = channel.read(buffer);

            if (count > 0) {
                String msg = new String(buffer.array());
                System.out.println("客户端端数据：" + msg);
                //向其它的客户端转发消息(排除自己)，专门写一个方法来处理
                sendInfoToOtherClients(msg, channel);
            }
        } catch (IOException e) {
            System.out.println(channel.getRemoteAddress() + "离线了");
            //取消注册
            key.cancel();
            //关闭通道
            channel.close();
        }
    }

    private void sendInfoToOtherClients(String msg, SocketChannel self) {
        System.out.println("服务器转发消息中...");

        try {
            for (SelectionKey key : selector.keys()) {
                Channel targetChannel = key.channel();

                // 1.排除自己的channel
                if (targetChannel instanceof SocketChannel && targetChannel != self) {
                    SocketChannel dest = (SocketChannel) targetChannel;
                    ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                    dest.write(buffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();

    }
}
