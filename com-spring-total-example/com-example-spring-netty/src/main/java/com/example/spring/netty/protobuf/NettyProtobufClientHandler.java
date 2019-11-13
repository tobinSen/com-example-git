package com.example.spring.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class NettyProtobufClientHandler extends SimpleChannelInboundHandler<DataInfo1.outer_message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo1.outer_message msg) throws Exception {

    }

    /**
     * 1.动态实现protoBuf（多协议）
     * 2.如何使用git来进行proto文件在client 和server 中共享？
     * git submodule:git仓库里面的一个仓库
     *
     * serverProject:pull拉取，protoBuf仓库
     *
     * data.proto
     * protoBuf_java :本地编译，push到远程仓库
     *
     *
     * clientProject:pull拉取，protoBuf仓库
     *
     *
     * git subtree ：这个仓库会做个serverProject仓库的一个子仓库，然后一起进行提交和拉取
     * photoshop
     *     |
     *     |-- sub/
     *     |   |
     *     |   |--libpng/
     *     |       |
     *     |       |-- libpng.c
     *     |       |-- libpng.h
     *     |       |-- README.md
     *     |
     *     |-- photoshop.c
     *     |-- photoshop.h
     *     |-- main.c
     *     |-- README.md
     *
     * maven jar 方式
     *
     */

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);
        DataInfo1.outer_message message;
        if (0 == randomInt) {
            message = DataInfo1.outer_message.newBuilder()
                    .setDataType(DataInfo1.outer_message.DataType.StudentType)
                    .setStudent(DataInfo1.Student.newBuilder()
                            .setName("张三").setAge(11).setAddress("北京")
                            .build())
                    .build();
        } else if (1 == randomInt) {
            message = DataInfo1.outer_message.newBuilder()
                    .setDataType(DataInfo1.outer_message.DataType.DogType)
                    .setCat(DataInfo1.Cat.newBuilder()
                            .setName("小苗").setCity(1)
                            .build())
                    .build();
        } else {
            message = DataInfo1.outer_message.newBuilder()
                    .setDataType(DataInfo1.outer_message.DataType.DogType)
                    .setDog(DataInfo1.Dog.newBuilder()
                            .setName("小狗").setAge(12)
                            .build())
                    .build();
        }
        ctx.writeAndFlush(message);
    }
}
