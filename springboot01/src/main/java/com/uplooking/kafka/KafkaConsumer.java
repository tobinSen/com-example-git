package com.uplooking.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    /**
     * void foo（@Payload（“city.name”）String cityName）
     */
    @KafkaListener(topics = "${kafka.topic.order}", containerFactory = "KafkaListenerContainerFactory")
    public void kafkaListener(@Payload String message,
                              @Header String hearder,
                              Consumer consumer,
                              Acknowledgment acknowledgment,
                              ConsumerRecord consumerRecord) {
        System.out.println("监听消息==》" + message);
        //当期的偏移量==》kafka是根据偏移量进行消费的
        long offset = consumerRecord.offset();

    }
}
