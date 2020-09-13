package com.example.spring.mpush;

import com.alibaba.fastjson.JSON;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

@Configuration
@IntegrationComponentScan
public class MqttProductConfig {
    //step5
    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        // MqttConnectOptions用来设置MQTT中固定头字段，包括will、ip、port、心跳间隔、是否保持上次连接，用户名和密码等连接属性设置
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        // 设置连接的账号和密码
        mqttConnectOptions.setUserName("guest");
        mqttConnectOptions.setPassword("guest".toCharArray());
        // 设置连接地址，支持集群模式
        String[] hostArray = new String[]{"tcp://127.0.0.1:1883"};
        mqttConnectOptions.setServerURIs(hostArray);
        // 设置长连接最大时长（秒），默认为1分钟
        mqttConnectOptions.setKeepAliveInterval(2);
        // 允许同时发送多少条消息（QOS1未收到PUBACK或QOS2未收到PUBCOMP的消息）
        mqttConnectOptions.setMaxInflight(100000);
        // 是否保持上次连接，
        mqttConnectOptions.setCleanSession(false);

        // 设置遗嘱消息
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload("state:offline".getBytes());
        mqttConnectOptions.setWill("topic/abc", JSON.toJSONString(mqttMessage).getBytes(), 1, true);
        return mqttConnectOptions;
    }

    // step4
    @Bean
    public MqttPahoClientFactory mqttClientFactory() throws MqttException {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    // step3 点对点路由通道
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    // step2 可调用Spring的Bean来处理消息，并将处理后的结果输出到指定的消息通道
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MqttPahoMessageHandler outbound1() throws MqttException {
        // 在这里进行mqttOutboundChannel的相关设置
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("publishClient", mqttClientFactory());
        messageHandler.setAsync(true); //如果设置成true，发送消息时将不会阻塞。
        messageHandler.setAsyncEvents(true);// 消息发送和传输完成会有异步的通知回调
        messageHandler.setDefaultTopic("topic");
        return messageHandler;
    }

    // step1 网关入口，即Spring Intergration的消息入口
    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface MqttGateway {
        // 指定topic、qos、retained进行消息发送
        void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, @Header(MqttHeaders.RETAINED) boolean retained, String payload);
    }
}
