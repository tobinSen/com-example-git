package com.example.spring.netty.tcp.client;

import com.example.spring.netty.tcp.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ChatClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //使用客户端发送10条记录

        for (int i = 0; i < 10; i++) {
            String msg = "今天天气冷，吃火锅";
            byte[] content = msg.getBytes(CharsetUtil.UTF_8);
            int length = content.length;
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setContent(content);
            messageProtocol.setLen(length);
            //发送内容
            ctx.writeAndFlush(messageProtocol);

        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

    }
}
