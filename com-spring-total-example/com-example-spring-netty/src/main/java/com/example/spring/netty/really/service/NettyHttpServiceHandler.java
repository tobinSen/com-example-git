package com.example.spring.netty.really.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class NettyHttpServiceHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "-->" + msg);
        ctx.channel().writeAndFlush("from server:" + UUID.randomUUID());

    }
}
