package com.example.spring.netty.tcp.client;

import com.example.spring.netty.tcp.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol> {

    //编码的时候，指定编码器，编码的长度和内容
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("被调用...");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
