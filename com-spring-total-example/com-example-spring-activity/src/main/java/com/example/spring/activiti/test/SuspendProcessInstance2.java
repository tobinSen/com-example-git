package com.example.spring.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 对单个流程实例进行挂起
 */
public class SuspendProcessInstance2 {

    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        //查询流程实例对象
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId("2501").singleResult();

        boolean suspended = processInstance.isSuspended();

        String processInstanceId = processInstance.getId();

        //如果实例2501,已经挂起，如果此时要让该实例继续执行，问是否抛出异常，抛出异常
        if (suspended) {
            //说明是暂停状态，可以激活流程实例
            runtimeService.activateProcessInstanceById(processInstanceId);
            System.out.println("流程实例" + processInstanceId + "激活");
        } else {
            //挂起
            runtimeService.suspendProcessInstanceById(processInstanceId);
            System.out.println("流程实例" + processInstanceId + "挂起");
        }

    }
}
