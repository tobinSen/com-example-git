package com.example.spring.canel.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    public final static String ZK_CONNECTION = "XXX.XXX.XXX.XXX:2181";
    public final static String BROKER_LIST_ADDRESS = "XXX.XXX.XXX.XXX:9092";
    public final static String GROUP_ID = "group1";
    public final static String TOPIC = "USER-DATA";

    public static String getZkConnection() {
        return ZK_CONNECTION;
    }

    public static String getBrokerListAddress() {
        return BROKER_LIST_ADDRESS;
    }

    public static String getGroupId() {
        return GROUP_ID;
    }

    public static String getTOPIC() {
        return TOPIC;
    }
}
