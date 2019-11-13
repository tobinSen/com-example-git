package com.example.spring.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyProtobufServerHandler extends SimpleChannelInboundHandler<DataInfo1.outer_message> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo1.outer_message msg) throws Exception {
        DataInfo1.outer_message.DataType dataType = msg.getDataType();
        if (dataType == DataInfo1.outer_message.DataType.StudentType) {
            DataInfo1.Student student = msg.getStudent();
            System.out.println(student.getName());
            System.out.println(student.getAge());
            System.out.println(student.getAddress());

        } else if (dataType == DataInfo1.outer_message.DataType.CatType) {
            DataInfo1.Cat cat = msg.getCat();
            System.out.println(cat.getName());
            System.out.println(cat.getCity());

        } else {
            DataInfo1.Dog dog = msg.getDog();
            System.out.println(dog.getName());
            System.out.println(dog.getAge());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
