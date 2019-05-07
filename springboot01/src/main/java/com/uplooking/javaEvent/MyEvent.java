package com.uplooking.javaEvent;

import java.util.EventObject;

/**
 * java的事件需要继承EventObject
 */
public class MyEvent extends EventObject {

    private static final long serialVersionUID = 1L;
    private int sourceState;

    public MyEvent(Object source) {
        super(source);
        sourceState = ((Source) source).getFlag();
    }

    public int getSourceState() {
        return sourceState;
    }

}