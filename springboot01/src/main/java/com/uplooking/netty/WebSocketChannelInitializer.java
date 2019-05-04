package com.uplooking.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {


    //初始化通道
    //在这个方法中去加载对应的ChannelHandler
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        //获取管道，将一个一个的ChannelHandler添加到管道中
        ChannelPipeline pipeline = socketChannel.pipeline();
        //添加一个http的编解码器
        pipeline.addLast(new HttpServerCodec());
        //添加一个用于支持大数据流的支持
        pipeline.addLast(new HttpObjectAggregator(1024*64));
        //需要指定接收请求的路由
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        //添加自定义的Handler
        pipeline.addLast(new ChatHandler());
    }
}
