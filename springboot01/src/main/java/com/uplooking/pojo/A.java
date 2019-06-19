package com.uplooking.pojo;

import javafx.util.Pair;

public abstract class A<T extends B & D & C> {
    //B -->class interface
    //D & C -->interface
    public abstract Pair<D, C> test(T t);
}
