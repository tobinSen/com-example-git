package com.example.spring.rocketmq.producer.producer;

import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

@Component
public class MqProducer {

    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    @Autowired
    private DefaultMQProducer producer;

    public void sendMessage() throws Exception {
        Message message = new Message("MQ-topic", "MQ-tag", "first send MQ".getBytes());
        message.setKeys("MQ-key"); //key是消息的唯一标识，可以快速定位到某个消息
        message.setDelayTimeLevel(0);//不延时发送
        producer.send(message);

        //顺序消息
        producer.send(message, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                Integer index = (Integer) arg;
                return mqs.get(index);
            }
        }, 0);//指定队列保证顺序 0=arg
        System.out.println("first send mq");

        // producer -->rocketMQ(pre) -->consumer
        // 执行service层本地事务 <--
        //                      -->发送消息
        //                     <--事务回查
        //rocketmq的事务消息，本地事务 + 消息事务
        TransactionMQProducer mqProducer = new TransactionMQProducer();
        TransactionSendResult result = mqProducer.sendMessageInTransaction(message, "hello-transaction");
        mqProducer.setTransactionListener(new TransactionListener() {
            //本地事务执行
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                //本地事务
                String transactionId = message.getTransactionId();
                localTrans.put(transactionId, 0);//0成功 1失败;
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            //消息回查
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                String transactionId = messageExt.getTransactionId();
                Integer statu = localTrans.get(transactionId);
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        //配置线程池
        mqProducer.setExecutorService(Executors.newSingleThreadExecutor());

    }

}
