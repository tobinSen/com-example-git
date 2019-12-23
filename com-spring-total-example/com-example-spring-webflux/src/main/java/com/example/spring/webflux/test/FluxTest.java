package com.example.spring.webflux.test;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Random;

/**
 * webFLux ： 4点
 * mono  flux
 * RouteFunction  handleFunction
 */
public class FluxTest {

    public static void main(String[] args) {

        Flux.just("flux").subscribe(System.out::println);
        Flux.fromArray(new Integer[]{1, 2, 3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.range(1, 4).subscribe(System.out::println);

        Flux.generate(sink -> {
            sink.next("flux generate");
            sink.complete(); //无限流，complete()后就停止
        }).subscribe(System.out::println);

        final Random r = new Random();
        Flux.generate(ArrayList::new, (list, sink) -> {
            int i = r.nextInt(10);
            list.add(i);
            sink.next(i); //next和complete是共用的
            if (i == 9) {
                sink.complete();
            }
            return list;
        }).subscribe(System.out::println);

        Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
            }
            sink.complete();
        }).subscribe(System.out::println);

        Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);

        Flux.range(1, 20).take(10).subscribe(System.out::println);
        Flux.range(1, 20).takeLast(10).subscribe(System.out::println);
        Flux.range(1, 20).takeWhile(i -> i < 10).subscribe(System.out::println);
        Flux.range(1, 20).takeUntil(i -> i == 10).subscribe(System.out::println);

        System.out.println("-------------------------------------------");
        Flux.just("a", "b", "c")
                .zipWith(Flux.just("e", "f", "g", "h", "i"))
                .subscribe(System.out::println);
    }


}


