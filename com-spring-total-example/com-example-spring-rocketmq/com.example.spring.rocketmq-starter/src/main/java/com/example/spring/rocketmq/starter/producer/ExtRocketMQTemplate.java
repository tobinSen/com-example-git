package com.example.spring.rocketmq.starter.producer;

import org.apache.rocketmq.spring.annotation.ExtRocketMQTemplateConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

//定制化RocketMQTemplate
@Component
@ExtRocketMQTemplateConfiguration(nameServer = "127.0.0.1:9876")
public class ExtRocketMQTemplate extends RocketMQTemplate {


}
