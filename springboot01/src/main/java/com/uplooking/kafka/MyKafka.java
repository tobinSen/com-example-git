package com.uplooking.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class MyKafka {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void test() {
        kafkaTemplate.send("topic", "data");
    }
}
