package com.example.spring.mpush;

import com.alibaba.fastjson.JSON;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttSendController {
    @Autowired
    private MqttProductConfig.MqttGateway gateway;
    @Autowired
    private MqttPahoMessageHandler messageHandler;

    @GetMapping(value = "/sendMqtt")
    public String testSendMqtt() {
        MqttMessage mqttMessage = new MqttMessage();
        // 消息id，这里的消息id存在与消息体中，并不是我们可变报头中的报文唯一标识
        mqttMessage.setId(1111);
        // 消息荷载
        mqttMessage.setPayload(("message: ").getBytes());
        // 将消息序列化成JSON发送给对应的topic并且指定消息质量和是否未保留消息
        gateway.sendToMqtt("topic/abc", 1, false, JSON.toJSONString(mqttMessage));
        return "success";
    }
}
