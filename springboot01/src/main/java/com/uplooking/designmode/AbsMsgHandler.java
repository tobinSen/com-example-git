package com.uplooking.designmode;

public abstract  class AbsMsgHandler implements MsgHandler{

    @Override
    public void getHandler(int type) {
        //进行获取,实现类
        test();
    }

    protected abstract  void test();
}
