package com.example.spring.websocket.sockJs;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public class SockJSController {

    //页面请求
    @GetMapping("/socket2")
    public ModelAndView socket2() {//@PathVariable String userId
        ModelAndView mav = new ModelAndView("html/socket2");
        //mav.addObject("userId", userId);
        return mav;
    }

    /**
     * 这个方法是接收客户端发送功公告的WebSocket请求，使用的是@MessageMapping
     */
    @MessageMapping("/change-notice")//客户端访问服务端的时候config中配置的服务端接收前缀也要加上 例：/app/change-notice
    //服务端发送到客户端订阅的地址，返回值
    @SendTo("/topic/notice")//config中配置的订阅前缀记得要加上
    public Message greeting(Message message) {
        System.out.println("服务端接收到消息：" + message.toString());
        //我们使用这个方法进行消息的转发发送！
        //this.simpMessagingTemplate.convertAndSend("/topic/notice", value);(可以使用定时器定时发送消息到客户端)
        //        @Scheduled(fixedDelay = 1000L)
        //        public void time() {
        //            messagingTemplate.convertAndSend("/system/time", new Date().toString());
        //        }
        //也可以使用sendTo发送
        return message;
    }
}
