package com.example.spring.webssh.config;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface WebSSHService {

    /**
     * 初始化ssh连接
     */
    void initConnection(WebSocketSession session);

    /**
     * 处理客户段发的数据
     */
    void recvHandle(String buffer, WebSocketSession session);

    /**
     * 数据写回前端 for websocket
     */
    void sendMessage(WebSocketSession session, byte[] buffer) throws IOException;

    /**
     * 关闭连接
     */
    void close(WebSocketSession session);
}
