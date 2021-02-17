package com.example.spring.weixin.demo;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 泛型类
 * 泛型方法
 * 泛型接口，1、直接指定  2、未指定
 * <p>
 * 抽象类和接口的区别：
 * 抽象类：
 * 成员变量：变量、常量
 * 构造方法：有
 * 成员方法：实例方法、抽象方法
 * <p>
 * <p>
 * 接口：
 * 成员变量：常量          public static final
 * 成员方法：抽象方法       public abstract
 */
public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {


    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
    }

    // 并行的时候，合并操作
    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        return t -> t;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT));
    }
}
