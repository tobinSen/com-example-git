package com.example.spring.rocketmq.consumer.configuration;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.List;

@Configuration
public class MqConfiguration implements InitializingBean, DisposableBean {

    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.groupName}")
    private String groupName;

    @Value("${rocketmq.consumer.instanceName}")
    private String instanceName;

    @Value("#{'${rocketmq.consumer.subscriptionList}'.split(';')}")
    private List<String> subscriptionList;

    private DefaultMQPushConsumer consumer;


    @Override
    public void destroy() throws Exception {
        if (null != consumer) {
            consumer.shutdown();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setInstanceName(instanceName);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setVipChannelEnabled(false);
        consumer.setConsumeMessageBatchMaxSize(1); //只能处理一条数据
        //consumer.setMessageModel(MessageModel.BROADCASTING);//广播模式
        /**
         * 集群模式：一个消息只能别一个消费者消费
         * 广播模式：每个消息都能被消费者消费
         */

        //订阅topic
        for (String subscription : subscriptionList) {
            String[] subscriptionArray = subscription.split(":"); //topic:tag
            if (StringUtils.isEmpty(subscriptionArray[1])) {
                subscriptionArray[1] = "*";
            }
            consumer.subscribe(subscriptionArray[0], subscriptionArray[1]);
        }
        //监听(Lamdba表达式中，final修饰说明不能使用lamdba，没有子类，只能进行强制转换)
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            MessageExt message = msgs.get(0);
            String content = message.getBody().toString();
            String tags = message.getTags();

            if ("MQ-tag".equals(tags)) {
                System.out.println("first consumer message" + content.toString());
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                return null;
            }
        });
        //启动
        consumer.start();
    }
}
