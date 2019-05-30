package com.uplooking.designmode;

public class ztMsgHandler extends AbsMsgHandler {


    @Override
    protected void test() {
        //具体的实现逻辑
    }

    //这就是独立的标识
    @Override
    public int getType() {
        return 0;
    }
}
