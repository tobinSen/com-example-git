package com.example.spring.activiti.test.activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 业务表-->activiti进行管理 businessKey
 */
public class BusinessKeyAdd {

    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        //启动流程实例，同时还要指定业务处理表示businessKey，它本身就是请假单Id
        //参数1：流程定义key  参数2：业务标识businessKey
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday", "1001");

        String businessKey = processInstance.getBusinessKey();
        System.out.println("businessKey:" + businessKey);

    }
}
