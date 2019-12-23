package com.example.spring.webflux.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonController {

    /**
     * Mono：实现发布者，并返回 0 或 1 个元素
     * <p>
     * Mono 常用的方法有：
     * <p>
     * Mono.create()：使用 MonoSink 来创建 Mono
     * Mono.justOrEmpty()：从一个 Optional 对象或 null 对象中创建 Mono。
     * Mono.error()：创建一个只包含错误消息的 Mono
     * Mono.never()：创建一个不包含任何消息通知的 Mono
     * Mono.delay()：在指定的延迟时间之后，创建一个 Mono，产生数字 0 作为唯一值
     * <p>
     * Flux：实现发布者，并返回 N 个元素
     * fromIterable(Iterable<? extends T> it) 可以发布 Iterable 类型的元素。
     * 当然，Flux 也包含了基础的操作：map、merge、concat、flatMap、take
     */
    //基于WebMVC注解的方式
    @GetMapping("/mono")
    public Mono<String> mono() {   // 【改】返回类型为Mono<String>
        return Mono.just("Welcome to reactive world ~");     // 【改】使用Mono.just生成响应式数据
    }

    @GetMapping("/flux")
    public Flux<String> flux() {   // 【改】返回类型为Mono<String>
        List<String> list = new ArrayList<>();
        list.add("String");
        return Flux.fromIterable(list);
    }
//==============================function===========================================


//    @Bean
//    public RouterFunction<ServerResponse> helloXttblog(XttblogHandler handler) {
//        return RouterFunctions
//                .route(RequestPredicates.GET("/hello")
//                                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
//                        handler::helloXttblog);
//    }
//
//    class XttblogHandler {
//        public Mono<ServerResponse> helloXttblog(ServerRequest request) {
//            return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
//                    .body(BodyInserters.fromObject("Hello, www.xttblog.com !"));
//
//        }
//    }
}
