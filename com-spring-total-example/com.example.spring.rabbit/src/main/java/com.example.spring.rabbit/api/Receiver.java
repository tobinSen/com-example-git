package com.example.spring.rabbit.api;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Receiver {
    private final static String QUEUE_NAME = "TestMQ";

    public static void main(String[] args) throws Exception {

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

        //申明接收消息的队列，与发送消息队列"hello"对应
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        //The extra DefaultConsumer is a class implementing the Consumer interface
        //we'll use to buffer the messages pushed to us by the server.
        Consumer consumer = new DefaultConsumer(channel) {

            //重写DefaultConsumer中handleDelivery方法，在方法中获取消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" 收到了消息: '" + message + "'");
                // 手动确认消息接受成功
                /**
                 * 首先需要讲清楚一个概念「deliveryTag」，即投递消息唯一标识符，它是一个「单调递增」的Long类型正整数。
                 * 假设此次basicAck的tag为123130，如果multiple=false，
                 * 那么表示只确认签收这一条消息。如果multiple=true，那么表示确认签收tag小于或等于123130的所有消息
                 */
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, false/*关闭consumer自动确认机制*/, consumer);
    }

}
