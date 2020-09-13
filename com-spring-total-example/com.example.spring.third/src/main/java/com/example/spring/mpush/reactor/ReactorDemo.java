package com.example.spring.mpush.reactor;

import org.assertj.core.util.Arrays;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorDemo {
    public static void main(String[] args) {
        Flux.just(123, 4, 4);
        Mono.just(1);

        Flux.fromArray(Arrays.array(1232));

    }

    
}
