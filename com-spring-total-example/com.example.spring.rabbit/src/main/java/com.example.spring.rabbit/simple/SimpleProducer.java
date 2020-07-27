package com.example.spring.rabbit.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SimpleProducer {

    public static void main01(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(AMQP.PROTOCOL.PORT);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        /**
         * String queue,        :队列名称
         * boolean durable,     :是否持久化队列
         * boolean exclusive,   :1、当连接关闭时connection.close()该队列是否会自动删除
         *                       2、一般等于true的话用于一个队列只能有一个消费者来消费的场景
         * boolean autoDelete,  :是否当所有消费者客户端连接断开时自动删除队列
         * Map<String, Object> arguments :队列中的消息什么时候会自动被删除
         *
         */
        channel.queueDeclare("hello_queue", false, false, false, null);
        channel.basicPublish("", "hello_queue", null, "hello".getBytes(StandardCharsets.UTF_8));

        channel.close();
        connection.close();
    }

    public static void main02(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(AMQP.PROTOCOL.PORT);    // 5672
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //必须要申明一个队列
        channel.queueDeclare("hello_queue", false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println(" [C] Received '" + message + "'");

                /*手动ack*/
                channel.basicAck(envelope.getDeliveryTag(), false);
            }

        };

        // 订阅消息 自动ack，消息到达consumer就ack了
        channel.basicConsume("hello_queue", false/*关闭自动ack*/, consumer);

    }

    public static void main03(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(AMQP.PROTOCOL.PORT);
        factory.setUsername("mengday");
        factory.setPassword("mengday");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String QUEUE_NAME = "queue.work";
        String ROUTING_KEY = "task";
        String EXCHANGE_NAME = "amqp.rabbitmq.work";
        // 声明队里(方式一：持久化)
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 声明交换机：指定交换机的名称和类型(direct)
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        // 循环发布多条消息
        for (int i = 0; i < 10; i++) {
            String message = "Hello RabbitMQ " + i;
            //方式三：
            AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder();
            properties.deliveryMode(2);  // 设置消息是否持久化，1： 非持久化 2：持久化
            AMQP.BasicProperties basicProperties = properties.build();

            //方式二：
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN/*basicProperties*/, message.getBytes("UTF-8"));
        }

        // 关闭资源
        channel.close();
        connection.close();

    }

    public static void main04(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(AMQP.PROTOCOL.PORT);    // 5672
        factory.setUsername("mengday");
        factory.setPassword("mengday");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 设置每次从队列获取消息的数量
        channel.basicQos(1);

        // 声明一个队列
        String QUEUE_NAME = "queue.work";
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);

                try {
                    System.out.println(" [C] Received '" + message + "', 处理业务中...");
                    // 手动应答
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // 订阅消息, false: 表示手动应答,需要手动调用basicAck()来应答
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

    public static void main05(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(AMQP.PROTOCOL.PORT);
        factory.setUsername("mengday");
        factory.setPassword("mengday");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        Map<String, Object> heardersMap = new HashMap<String, Object>();
        heardersMap.put("api", "login");
        heardersMap.put("version", 1.0);
        heardersMap.put("radom", UUID.randomUUID().toString());
        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder().headers(heardersMap);

        String message = "Hello RabbitMQ!";
        String EXCHANGE_NAME = "exchange.hearders";
        channel.basicPublish(EXCHANGE_NAME, "", properties.build(), message.getBytes("UTF-8"));

        channel.close();
        connection.close();
    }

    public static void main06(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(AMQP.PROTOCOL.PORT);    // 5672
        factory.setUsername("mengday");
        factory.setPassword("mengday");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String EXCHANGE_NAME = "exchange.hearders";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);

        String queueName = channel.queueDeclare().getQueue();
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("x-match", "any");
        arguments.put("api", "login");
        arguments.put("version", 1.0);
        arguments.put("dataType", "json");


        // 队列绑定时需要指定参数,注意虽然不需要路由键但仍旧不能写成null，需要写成空字符串""
        channel.queueBind(queueName, EXCHANGE_NAME, "", arguments);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println(message);
            }
        };

        channel.basicConsume(queueName, true, consumer);

    }

}
