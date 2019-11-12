package com.example.spring.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        //处理http协议的编解码处理器
        pipeline.addLast(new HttpServerCodec());
        //以块的方式进行写的处理器
        pipeline.addLast(new ChunkedWriteHandler());
        //对http消息进行聚合（netty会将消息进行分段(分块)进行发送）
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new WebSocketServerHandler());
    }
}
