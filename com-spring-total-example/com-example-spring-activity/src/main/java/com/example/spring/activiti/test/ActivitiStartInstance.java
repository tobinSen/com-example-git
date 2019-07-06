package com.example.spring.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 启动流程实例(前提：流程定义要部署)
 * 影响的表：
 * act_hi_actinst       已完成实例
 * act_hi_identitylink  参与者信息
 * act_hi_proinst    流程实例
 * act_hi_taskinst   任务实例
 * act_ru_execution   执行表
 * act_ru_identitylink 参与者信息
 * act_ru_task  任务表
 */
public class ActivitiStartInstance {

    public static void main(String[] args) {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RunService对象
        RuntimeService runtimeService = processEngine.getRuntimeService();

        //3.创建流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday");

        //4.输出实例的相关信息
        System.out.println("流程部署Id" + processInstance.getDeploymentId());
        System.out.println("流程定义Id" + processInstance.getProcessInstanceId());
        System.out.println("流程实例Id" + processInstance.getId());
        System.out.println(processInstance.getActivityId());//流程图中的步骤Id
    }
}
