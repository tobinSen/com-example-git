package com.uplooking.java_spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class TestMain {

    public static void main(String[] args) {
        IOperation plus = new PlusOperation();

        IOperation division = new DivisionOperation();

        System.out.println(plus.operation(1, 2));
        System.out.println(division.operation(2, 3));

        ServiceLoader<IOperation> operations = ServiceLoader.load(IOperation.class);
        Iterator<IOperation> iterator = operations.iterator();
        System.out.println("classPath:" + System.getProperty("java.class.path"));
        while (iterator.hasNext()) {
            IOperation operation = iterator.next();
            System.out.println(operation.operation(2, 3));
        }
    }
}
