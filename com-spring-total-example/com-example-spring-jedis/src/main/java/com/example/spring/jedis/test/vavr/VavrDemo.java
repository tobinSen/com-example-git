package com.example.spring.jedis.test.vavr;

import io.vavr.Function3;
import io.vavr.Tuple;
import io.vavr.Tuple2;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.control.Try.run;

public class VavrDemo {

    public static void main1(String[] args) {
        Tuple2<String, Integer> tuple2 = Tuple.of("Hello", 100);
        Tuple2<String, Integer> updatedTuple2 = tuple2.map(String::toUpperCase, v -> v * 5);

        String result = updatedTuple2.apply((str, number) -> String.join(", ", str, number.toString()));
        System.out.println(result);


        Function3< Integer, Integer, Integer, Integer> function3 = (v1, v2, v3) -> (v1 + v2) * v3;
        Function3< Integer, Integer, Integer, Integer> composed = function3.andThen(v -> v * 100);
        int result1 = composed.apply(1, 2, 3);
        System.out.println(result1);
    }

    public static void main2(String[] args) {
        String input = "good";
        String result = Match(input).of(
                Case($("g"), "good"),
                Case($("b"), "bad"),
                Case($(), "unknown")
        );
        System.out.println(result);
    }

    public static void main(String[] args) {
        int value = -1;
        Match(value).of(
                Case($(v -> v > 0), o -> run(() -> System.out.println("> 0"))),
                Case($(0), o -> run(() -> System.out.println("0"))),
                Case($(), o -> run(() -> System.out.println("< 0")))
        );
        // 输出<  0
    }
}
