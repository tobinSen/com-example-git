package com.example.spring.netty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

//我们自定义一个handler，需要继承netty提供的handler
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 读取数据实际(这里我们可以读取客户端发送的消息)
     * channelHandlerContext ：上下文对象(管道pipeline) 通道channel 地址
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + ctx);
        System.out.println("currentThread:" + Thread.currentThread().getName());

        ChannelPipeline pipeline = ctx.pipeline();
        Channel channel = ctx.channel();

        ctx.channel().eventLoop().execute(() -> {
            System.out.println("server:");
        });

        ByteBuf buf = (ByteBuf) msg;

        System.out.println("客户端发送消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓存中，并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
