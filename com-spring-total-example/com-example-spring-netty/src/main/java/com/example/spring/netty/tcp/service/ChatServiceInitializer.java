package com.example.spring.netty.tcp.service;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class ChatServiceInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //进行指定解码
        pipeline.addLast(new MyMessageDecoder());
        pipeline.addLast(new ChatServiceHandler());
    }

}
