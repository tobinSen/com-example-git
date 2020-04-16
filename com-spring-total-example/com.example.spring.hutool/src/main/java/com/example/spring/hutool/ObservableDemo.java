package com.example.spring.hutool;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ObservableDemo {

    public static void main(String[] args) {
        Observable.fromCallable(() -> {
            System.out.println("12");
            return 0;
        }).map(i -> {
            System.out.println(i);
            return i;
        }).subscribeOn(Schedulers.io()).subscribe(System.out::println);
    }
}
