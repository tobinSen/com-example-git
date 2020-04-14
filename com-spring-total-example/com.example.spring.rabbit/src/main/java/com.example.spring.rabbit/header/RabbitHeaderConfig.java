package com.example.spring.rabbit.header;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitHeaderConfig {

    @Bean
    public Queue queueA() {
        return new Queue("header.a");
    }


    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange("headerExchange");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueA()).to(headersExchange()).whereAny("header.a").exist();
    }
}
