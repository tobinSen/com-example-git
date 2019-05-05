package com.uplooking.java_spi;

public class PlusOperation implements IOperation {


    @Override
    public int operation(int x, int y) {
        return x + y;
    }
}
