package com.example.spring.websocket.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class MyTextWebSocketHandler extends TextWebSocketHandler {

    private static Set<WebSocketSession> tmpCache = new HashSet<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获取接收到的数据
        String payload = message.getPayload();

        // 向客户端发送数据
        session.sendMessage(new TextMessage("response: " + payload));
    }

    //创建连接后
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        tmpCache.add(session);
        sendMsg("欢迎" + session.getId() + "进入聊天室");
    }

    //断开连接
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        tmpCache.remove(session);
        sendMsg(session.getId() + " 离开聊天室");
    }

    //通过遍历session来推送给每个人消息
    private void sendMsg(String msg) throws IOException {
        for (WebSocketSession session : tmpCache) {
            session.sendMessage(new TextMessage(msg + " | " + LocalDateTime.now()));
        }
    }
}
