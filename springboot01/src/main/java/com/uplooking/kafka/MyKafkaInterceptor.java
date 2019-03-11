package com.uplooking.kafka;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class MyKafkaInterceptor implements ProducerInterceptor<String, String> {

    private int errorCounter = 0;
    private int successCounter = 0;


    /**
     * 该方法封装进KafkaProducer.send方法中，即它运行在用户主线程中。
     * Producer确保在消息被序列化以及计算分区前调用该方法。
     * 用户可以在该方法中对消息做任何操作，但最好保证不要修改消息所属的topic和分区，
     * 否则会影响目标分区的计算
     */
    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        return new ProducerRecord(record.topic(), record.partition(), record.timestamp(),
                record.key(), System.currentTimeMillis() + "," + record.value());
    }

    /**
     * 该方法会在消息被应答或消息发送失败时调用，并且通常都是在producer回调逻辑触发之前。
     * onAcknowledgement运行在producer的IO线程中，
     * 因此不要在该方法中放入很重的逻辑，否则会拖慢producer的消息发送效率
     */
    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception exception) {

        // 统计成功和失败的次数
        if (exception == null) {
            successCounter++;
        } else {
            errorCounter++;
        }

    }

    //关闭interceptor，主要用于执行一些资源清理工作
    @Override
    public void close() {

    }

    //获取配置信息和初始化数据时调用
    @Override
    public void configure(Map<String, ?> map) {
    }
}
