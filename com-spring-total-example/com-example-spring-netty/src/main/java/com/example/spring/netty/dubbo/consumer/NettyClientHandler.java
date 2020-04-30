package com.example.spring.netty.dubbo.consumer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String result;
    private String param; //客户端调用参数

    //step 1
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    //step 4
    @Override
    public synchronized /*strictfp*/ void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        //唤醒等待的线程
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    //step 3
    //被代理对象调用，发送数据给服务器 -> wait -> 等待唤醒(channelRead) -> 返回结果
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(param);
        //等待channelRead 方法获取到了服务器的结果后，唤醒
        wait();
        return result; //服务方返回的结果
    }

    //step 2
    public void setParam(String param) {
        this.param = param;
    }
}
