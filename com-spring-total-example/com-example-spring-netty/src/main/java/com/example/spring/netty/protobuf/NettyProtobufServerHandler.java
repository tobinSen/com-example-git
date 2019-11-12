package com.example.spring.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyProtobufServerHandler extends SimpleChannelInboundHandler<DataInfo.Student> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Student msg) throws Exception {
        System.out.println(msg.getName());
        System.out.println(msg.getAge());
        System.out.println(msg.getAddress());
    }
}
