package com.example.spring.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyProtobufClientHandler extends SimpleChannelInboundHandler<DataInfo.Student> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Student msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        DataInfo.Student student = DataInfo.Student.newBuilder().setName("张三")
                .setAge(11).setAddress("北京").build();

        ctx.writeAndFlush(student);

    }
}
