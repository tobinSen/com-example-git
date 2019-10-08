package com.example.spring.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class HelloKafkaConsumer {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.deserializer", StringDeserializer.class);
        properties.put("value.deserializer", StringDeserializer.class);
        //消费者组group.id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test1");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        try {
            //再均衡监听器
            consumer.subscribe(Collections.singletonList("HELLO_TOPIC"), new ConsumerRebalanceListener() {
                //在均衡前
                @Override
                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {

                }

                //在均衡后
                @Override
                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

                }
            });
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(500);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format("topic:%s,分区：%d,偏移量：%d," +
                                    "key:%s,value:%s", record.topic(), record.partition(),
                            record.offset(), record.key(), record.value()));
                    //do my work
                    //打包任务投入线程池
                }
                //指定分区
                //consumer.seek(, );
                //同步提交-->堵塞
                consumer.commitSync();
                //异步提交，并回调
                consumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {

                    }
                });
            }//todo
            //Fixme
            //xxx
            //hack

        } finally {
            consumer.close();

        }
    }
}
