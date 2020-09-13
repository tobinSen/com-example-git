package com.example.spring.client;

import org.apache.servicecomb.saga.omega.context.annotations.SagaStart;
import org.apache.servicecomb.saga.omega.transaction.annotations.Compensable;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

public class BossTaskHandler implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("老板");
    }


    /**/
    @SagaStart(timeout = 10)
    public boolean transferMoney(String from, String to, int amount) {
        transferOut(from, amount);
        transferIn(to, amount);
        return false;
    }

    @Compensable(timeout = 5, compensationMethod = "cancel")
    public boolean transferOut(String from, int amount) {
        //repo.reduceBalanceByUsername(from, amount);
        return false;
    }

    public boolean cancel(String from, int amount) {
        //repo.addBalanceByUsername(from, amount);
        return false;
    }

    @Compensable(timeout = 5, compensationMethod = "cancel")
    public boolean transferIn(String from, int amount) {
        //repo.reduceBalanceByUsername(from, amount);
        return false;
    }
}
