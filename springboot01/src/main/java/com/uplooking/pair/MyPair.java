package com.uplooking.pair;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class MyPair {

    public static void main(String[] args) {
        Pair<String, Integer> pair = MutablePair.of("18", 16);
        Pair<String, Integer> pair1 = MutablePair.of(null, 15);
        System.out.println((pair.getKey()+"-->"+pair.getLeft()+"-->"+pair.getRight()+"-->"+pair.getValue()));

        Triple<Integer, Integer, Integer> of = MutableTriple.of(19, 12, 11);
        System.out.println(of);
    }

}
