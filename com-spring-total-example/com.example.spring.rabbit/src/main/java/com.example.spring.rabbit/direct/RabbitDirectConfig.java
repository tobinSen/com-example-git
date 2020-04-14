package com.example.spring.rabbit.direct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitDirectConfig {

    @Bean
    public Queue queueA() {
        return new Queue("direct.a");
    }

    @Bean
    public Queue queueB() {
        return new Queue("direct.b");
    }

    @Bean
    public DirectExchange directExchangeA() {
        return new DirectExchange("directExchange.a");
    }

    @Bean
    public DirectExchange directExchangeB() {
        return new DirectExchange("directExchange.b");
    }

    @Bean
    public Binding bindingA() {
        return BindingBuilder.bind(queueA()).to(directExchangeA()).with("direct.a");
    }

    @Bean
    public Binding bindingB() {
        return BindingBuilder.bind(queueB()).to(directExchangeB()).with("direct.b");
    }
}
