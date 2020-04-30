package com.example.spring.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试方法：使用telnet命令
 *
 * telnet 127.0.0.1 6666 进入telnet页面
 *
 */
public class BIOServer {

    public static void main(String[] args) throws IOException {
        //1.创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //tcp连接
        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务器开始启动...");

        while (true) {
            final Socket socket = serverSocket.accept();//阻塞
            System.out.println("连接到一个客户端...");

            executorService.execute(() -> {
                System.out.println("当前线程信息：" + Thread.currentThread().getId());

                //可以和客户端进行通讯
                handler(socket);
            });
        }
    }

    //和客户端进行通讯

    public static void handler(Socket socket) {
        try {

            System.out.println("当前线程信息：" + Thread.currentThread().getId());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();//阻塞
            //循环读取客户端端发送的数据
            while (true) {
                int read = inputStream.read(bytes);
                if (-1 != read) {
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
