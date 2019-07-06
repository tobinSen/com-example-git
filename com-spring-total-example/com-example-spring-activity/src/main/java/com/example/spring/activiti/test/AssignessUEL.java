package com.example.spring.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.HashMap;
import java.util.Map;

/**
 * 启动流程实例：动态设置assigness UEL表达式 ${assigness}
 */
public class AssignessUEL {

    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> map = new HashMap<>();
        map.put("assigness3", "zhangsan");
        map.put("assigness4", "lisi");
        map.put("assigness5", "wangwu");

        //启动流程实例，并设置assigness值
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday", map);

        System.out.println(processInstance.getName());
    }

    /**
     * 任务监听器是发生对应的任务相关事件时执行自定义java逻辑
     * 任务相当事件包括
     * Create:任务创建后触发
     * Assignment:任务分配后触发
     * Delete:任务完成后触发
     * All：所有事件发生都触发
     */
    //监听类
    class MyTaskListener implements TaskListener {

        @Override
        public void notify(DelegateTask delegateTask) {
            //这里指定任务负责人
            delegateTask.setAssignee("张三");
        }
    }

    /**
     * 流程变量：
     *      流程含有分支时候，用于判断
     *      创建时候：创建实例的时候
     * 类型：
     * 作用域：节点 local
     *        实例 global
     */
}
