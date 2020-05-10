package com.example.spring.rabbit.api;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    /*
     * 定义一个队列“hello”
     */
    private final static String QUEUE_NAME = "TestMQ";

    public static void main(String[] argv) throws IOException, TimeoutException {
        //创建一个连接
        ConnectionFactory factory = new ConnectionFactory();

        //连接本地，如果需要指定到服务，需在这里指定IP
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        Connection connection = factory.newConnection();

        //创建一个通道
        Channel channel = connection.createChannel();
        //开启provider到broker的confirm机制
        channel.confirmSelect();
//        channel.txCommit() 阻塞的
        //增加确认监听机制
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息已经ack，tag: " + deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                // 对于消费者没有ack的消息，可以做一些特殊处理
                System.out.println("消息被拒签，tag: " + deliveryTag);
            }
        });

        //申明通道发送消息的队列，把消息发送至消息队列‘hello’
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello 你好!";

        //Declaring a queue is idempotent - 如果队列不存在则会创建一个队列
        //消息内容为byte array, so可以自己随意编码
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" 已经发出的消息 :'" + message + "'");

        //消息发送完成后，关闭通道和连接
        channel.close();
        connection.close();
    }

}
