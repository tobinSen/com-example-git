package com.uplooking.java_spi;

public class DivisionOperation implements IOperation {


    @Override
    public int operation(int x, int y) {
        return x / y;
    }
}
