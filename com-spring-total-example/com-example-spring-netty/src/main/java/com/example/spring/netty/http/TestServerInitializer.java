package com.example.spring.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //netty提供的http编解码器
        ch.pipeline().addLast("HttpServerCodec", new HttpServerCodec())
                .addLast("httpServerHandler", new TestHttpServerHandler());

    }
}
