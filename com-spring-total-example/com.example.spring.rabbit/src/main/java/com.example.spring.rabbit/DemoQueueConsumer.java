package com.example.spring.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DemoQueueConsumer {

    /**
     * 消息消费
     *
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    //@RabbitHandler
    @RabbitListener(queues = "demoQueue")
    public void recieved(String msg) {
        System.out.println("[demoQueue] recieved message: " + msg);
    }

    //=======================fanout广播模式===================================
    @RabbitListener(queues = "fanout.a")
    public void recievedFanoutA(String msg) {
        System.out.println("[fanout.a] recieved message: " + msg);
    }

    @RabbitListener(queues = "fanout.b")
    public void recievedFanoutB(String msg) {
        System.out.println("[fanout.a] recieved message: " + msg);
    }

    @RabbitListener(queues = "fanout.c")
    public void recievedFanoutC(String msg) {
        System.out.println("[fanout.a] recieved message: " + msg);
    }


    //=======================topic主题模式===================================
    @RabbitListener(queues = "topic.a")
    public void recievedTopicA(String msg) {
        System.out.println("[fanout.a] recieved message: " + msg);
    }

    @RabbitListener(queues = "topic.b")
    public void recievedTopicB(String msg) {
        System.out.println("[fanout.a] recieved message: " + msg);
    }

    @RabbitListener(queues = "topic.c")
    public void recievedTopicC(String msg) {
        System.out.println("[fanout.a] recieved message: " + msg);
    }

    //=======================Direct主题模式===================================
    @RabbitListener(queues = "direct.a")
    public void recievedDirectA(String msg) {
        System.out.println("[direct.a] recieved message: " + msg);
    }

    @RabbitListener(queues = "direct.b")
    public void recievedDirectB(String msg) {
        System.out.println("[direct.b] recieved message: " + msg);
    }

    @RabbitListener(queues = "header.a")
    public void receivedHeader(@Payload Message message, @Header Map<String,Object> header) {
        System.out.println(message.toString());
    }
}
