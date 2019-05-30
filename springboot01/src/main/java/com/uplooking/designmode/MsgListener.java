package com.uplooking.designmode;

import org.springframework.beans.factory.ObjectProvider;

import java.util.concurrent.ConcurrentHashMap;

public class MsgListener {
    private ConcurrentHashMap<Integer, MsgHandler> msgMap = new ConcurrentHashMap<>();

    public MsgListener(ObjectProvider<MsgHandler[]> msgHandlerObjectProvider) {
        MsgHandler[] msgHandlers = msgHandlerObjectProvider.getIfAvailable();
        for (MsgHandler msgHandler : msgHandlers) {
            msgMap.put(msgHandler.getType(), msgHandler);
        }
    }


}
