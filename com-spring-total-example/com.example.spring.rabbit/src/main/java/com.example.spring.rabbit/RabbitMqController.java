package com.example.spring.rabbit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMqController {

    @Autowired
    private RabbitProducer rabbitProducer;

    @GetMapping("/sendDemoQueue")
    public Object sendDemoQueue() {
        rabbitProducer.sendDemoQueue();
        return "success";
    }

    //fanout广播模式
    @GetMapping("/sendFanout")
    public Object sendFanout() {
        rabbitProducer.sendFanout();
        return "success";
    }

    //Topic主题模式
    @GetMapping("/sendTopicTopicAB")
    public Object sendTopicTopicAB() {
        rabbitProducer.sendTopicTopicAB();
        return "success";
    }

    @GetMapping("/sendTopicTopicB")
    public Object sendTopicTopicB() {
        rabbitProducer.sendTopicTopicB();
        return "success";
    }

    @GetMapping("/sendTopicTopicBC")
    public Object sendTopicTopicBC() {
        rabbitProducer.sendTopicTopicBC();
        return "success";
    }

    //Direct直连模式
    @GetMapping("/sendDirectA")
    public Object sendDirectA() {
        rabbitProducer.sendDirectA();
        return "success";
    }

    @GetMapping("/sendDirectB")
    public Object sendDirectB() {
        rabbitProducer.sendDirectB();
        return "success";
    }

    @GetMapping("/sendHeaderA")
    public Object sendHeaderA() {
        rabbitProducer.sendHeaderA();
        return "success";
    }

}
