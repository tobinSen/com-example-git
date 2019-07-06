package com.example.spring.canel.product;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import java.util.Properties;

import static com.example.spring.canel.configuration.KafkaProperties.BROKER_LIST_ADDRESS;
import static com.example.spring.canel.configuration.KafkaProperties.TOPIC;

public class KafkaProduct {

    public static Logger log = Logger.getLogger(KafkaProduct.class);

    //kafka的链接地址要使用hostname 默认9092端口
    private static final String BROKER_LIST = BROKER_LIST_ADDRESS;

    private static KafkaProducer<String, String> producer = null;


    static {
        Properties configs = initConfig();
        producer = new KafkaProducer<String, String>(configs);
    }

    /*
    初始化配置
     */
    private static Properties initConfig() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

    public static void sendMsg(String msg) {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, msg);
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (null != e) {
                    log.info("send error" + e.getMessage());
                } else {
                    System.out.println("send success");
                }
            }
        });
    }

}

