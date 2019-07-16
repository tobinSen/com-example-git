package com.example.spring.rocketmq.producer.configuration;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfiguration implements DisposableBean {

    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.producer.groupName}")
    private String producerGroupName;

    @Value("${rocketmq.producer.instanceName}")
    private String producerInstanceName;

    @Value("10")
    private Integer retryTimes;

    private DefaultMQProducer producer;

    @Override
    public void destroy() throws Exception {
        if (null != producer) {
            producer.shutdown();
        }
    }

    @Bean
    public DefaultMQProducer defaultMQProducer() throws Exception {
        producer = new DefaultMQProducer();
        producer.setNamesrvAddr(namesrvAddr);
        producer.setProducerGroup(producerGroupName);
        producer.setInstanceName(producerInstanceName);
        producer.setRetryTimesWhenSendAsyncFailed(retryTimes);
        producer.setRetryTimesWhenSendFailed(retryTimes);
        producer.setVipChannelEnabled(false);//必须要设置为false否则会报错
        producer.start();
        return producer;
    }
}
