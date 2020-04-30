package com.example.spring.netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioFileChannel {

    public static void main01(String[] args) throws Exception {
        String str = "tongjian";
        FileOutputStream fos = new FileOutputStream("/Users/tongjian/Downloads/tongjian.txt");
        FileChannel fileChannel = fos.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.put(str.getBytes());

        byteBuffer.flip();//翻转

        //将buffer写入到channel
        fileChannel.write(byteBuffer);
        fos.close();
    }

    public static void main02(String[] args) throws Exception {
        File file = new File("/Users/tongjian/Downloads/tongjian.txt");
        FileInputStream fis = new FileInputStream(file);
        FileChannel fileChannel = fis.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        fileChannel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array()));
        fis.close();
    }

    public static void main03(String[] args) throws Exception {
        File source = new File("/Users/tongjian/Downloads/tongjian.txt");
        File target = new File("/Users/tongjian/Downloads/tongjian1.txt");

        FileInputStream sourceFis = new FileInputStream(source);
        FileChannel sourceChannel = sourceFis.getChannel();

        FileOutputStream targetFos = new FileOutputStream(target);
        FileChannel targetChannel = targetFos.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {
            byteBuffer.clear();//重置 第一次如果全部读取完了，position=limit,read的时候读取的就是0,不可能为-1了就一直读取了
            int read = sourceChannel.read(byteBuffer);
            if (read == -1) {
                break;
            }
            byteBuffer.flip();//翻转
            targetChannel.write(byteBuffer);
        }

        sourceFis.close();
        targetFos.close();

    }

    public static void main04(String[] args) throws Exception {
        File source = new File("/Users/tongjian/Downloads/tongjian.txt");
        File target = new File("/Users/tongjian/Downloads/tongjian.txt");

        FileInputStream fis = new FileInputStream(source);
        FileOutputStream fos = new FileOutputStream(target);

        FileChannel sourceChannel = fis.getChannel();
        FileChannel targetChannel = fos.getChannel();

        //通道转换
        targetChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

    }


    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffer = new ByteBuffer[2];
        byteBuffer[0] = ByteBuffer.allocate(5);
        byteBuffer[1] = ByteBuffer.allocate(3);


        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long l = socketChannel.read(byteBuffer);
                byteRead += 1;
                System.out.println("byteRead:" + byteRead);
            }
        }
    }
}
