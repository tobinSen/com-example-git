package com.example.spring.netty.really.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

    //服务端向客户端发送地址
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("服务端地址：" + ctx.channel().remoteAddress());

        System.out.println("client output" + msg);

        //客户端向服务器发送消息
        ctx.writeAndFlush("from client " + LocalDateTime.now());

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("来自于客户端问候!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
