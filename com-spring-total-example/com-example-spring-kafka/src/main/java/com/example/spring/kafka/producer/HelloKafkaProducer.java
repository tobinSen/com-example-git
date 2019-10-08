package com.example.spring.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

public class HelloKafkaProducer {

    public static void main(String[] args) {
        //1.设置kafka生产者属性，序列化
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //2.构建生产者通过属性
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        try {
            //3.消息
            ProducerRecord<String, String> record;
            try {
                // topic + key + content
                //public ProducerRecord(String topic, Integer partition, K key, V value) 指定分区

                record = new ProducerRecord<>("HELLO_TOPIC", "teacher02", "lison");
                Future<RecordMetadata> future = producer.send(record);
                RecordMetadata recordMetadata = future.get();

                System.out.println("topic" + recordMetadata.topic());
                System.out.println("partition" + recordMetadata.partition());
                System.out.println("offset" + recordMetadata.offset());//偏移量应该是三者关系

                System.out.println("message is sent.");

                System.out.println("-------------------------------------------");

                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if (null != exception) {
                            exception.printStackTrace();
                        }
                        if (null == metadata) {
                            System.out.println(metadata.topic() + "--" + metadata.partition() + "--" + metadata.offset());
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            producer.close();
        }
    }
}
