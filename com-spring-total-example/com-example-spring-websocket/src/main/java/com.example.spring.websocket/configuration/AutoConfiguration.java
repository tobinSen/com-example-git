package com.example.spring.websocket.configuration;

import com.example.spring.websocket.handler.MyTextWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
//@EnableWebMvc
public class AutoConfiguration implements WebSocketConfigurer {

    /**
     * 添加注解 @EnableWebSocket 表示开启了webocket服务
     * registerWebSocketHandlers 这个方法用来注册websocket服务，
     * 上面表示将路径 wsdemo 的请求转发到 WebSocketHandler
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "wsdemo");
    }

    @Bean
    public MyTextWebSocketHandler webSocketHandler() {
        return new MyTextWebSocketHandler();
    }
}
