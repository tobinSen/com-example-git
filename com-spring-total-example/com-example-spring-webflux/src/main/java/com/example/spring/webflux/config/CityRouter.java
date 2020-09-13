package com.example.spring.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

@Configuration
public class CityRouter {

    //router ->handler进行绑定
    @Bean
    public RouterFunction<ServerResponse> routeCity(CityHandler cityHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), cityHandler::helloCity);
    }


    /**
     * 1、RouterFunction --> HandlerFunction
     * 2、RouterFunction --> HttpHandler
     *                   --> WebHandler
     * 3、ReactorHttpHandlerAdapter --> 将HttpHandler适配到netty的channel
     * 4、HttpServer --> 启动netty的reactive,绑定适配器
     */
    public void createReactorServer() {
        //路由和 handler 适配
        RouterFunction<ServerResponse> route = routeCity(null);
        HttpHandler httpHandler = toHttpHandler(route);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        //创建服务器
        //HttpServer httpServer = HttpServer.create();
        //httpServer.handle(adapter).bindNow();
    }

}
