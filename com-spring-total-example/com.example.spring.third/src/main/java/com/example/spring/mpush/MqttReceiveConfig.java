package com.example.spring.mpush;

import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

//通过rabbitMQ来实现MQTT协议

@Configuration
public class MqttReceiveConfig {

    //step5
    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName("guest");
        mqttConnectOptions.setPassword("guest".toCharArray());
        String[] hostArray = new String[]{"tcp://127.0.0.1:1883"};
        mqttConnectOptions.setServerURIs(hostArray);
        mqttConnectOptions.setKeepAliveInterval(2);
        mqttConnectOptions.setCleanSession(false);
        return mqttConnectOptions;
    }

    //step4
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    // step3 点对点路由通道
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    // step2 定义入站消息适配器
    @Bean
    public MessageProducer inbound() {
        // 定义监听的topic名称ClientId等信息
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("consumerClient", mqttClientFactory(), "topic/abc");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    // step1  可调用Spring的Bean来处理消息，并将处理后的结果输出到指定的消息通道
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            MqttMessage mqttMessage = JSONObject.parseObject(message.getPayload().toString(), MqttMessage.class);
            // 这里可以根据不同的topic name处理不同的业务逻辑
            String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
        };
    }
}
