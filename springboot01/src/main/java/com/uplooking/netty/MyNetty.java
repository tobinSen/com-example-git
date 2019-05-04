package com.uplooking.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyNetty {

    public static void main(String[] args) {
        //创建线程池
        NioEventLoopGroup mainGroup = new NioEventLoopGroup();
        NioEventLoopGroup subGroup = new NioEventLoopGroup();

        try {


        //创建netty服务器启动对象
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //初始化服务器启动对象
        serverBootstrap
                //指定使用上面的两个线程池
                .group(mainGroup,subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WebSocketChannelInitializer());
            //绑定服务器端口，以同步的方式启动服务器
        ChannelFuture future = serverBootstrap.bind(9090).sync();
        //等待服务器挂壁
        future.channel().closeFuture().sync();
        }catch (Exception e){

        }finally {
            //优雅关闭服务器
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }
}
