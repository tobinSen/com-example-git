package com.example.spring.rabbit.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTopicConfig {

    //=================== topic主题模式  ====================

    @Bean
    public Queue topiocA() {
        return new Queue("topic.a");
    }

    @Bean
    public Queue topicB() {
        return new Queue("topic.b");
    }

    @Bean
    public Queue topicC() {
        return new Queue("topic.c");
    }

    /**
     * 定义个topic交换器
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        // 定义一个名为fanoutExchange的fanout交换器
        return new TopicExchange("topicExchange");
    }

    //topicA的key为topic.msg 那么他只会接收包含topic.msg的消息
    //
    //topicB的key为topic.#那么他只会接收topic开头的消息
    //
    //topicC的key为topic.*.z那么他只会接收topic.x.z这样格式的消息

    /**
     * 将定义的topicA队列与topicExchange交换机绑定
     *
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeWithA() {
        return BindingBuilder.bind(topiocA()).to(topicExchange()).with("topic.msg");
    }

    /**
     * 将定义的topicB队列与topicExchange交换机绑定
     *
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeWithB() {
        return BindingBuilder.bind(topicB()).to(topicExchange()).with("topic.#");
    }

    /**
     * 将定义的topicC队列与topicExchange交换机绑定
     *
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeWithC() {
        return BindingBuilder.bind(topicC()).to(topicExchange()).with("topic.*.z");
    }
}
