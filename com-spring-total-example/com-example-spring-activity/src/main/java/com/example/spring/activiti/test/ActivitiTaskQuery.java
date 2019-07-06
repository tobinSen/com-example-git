package com.example.spring.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * 查询当前用户任务列表
 */
public class ActivitiTaskQuery {
    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到taskService对象
        TaskService taskService = processEngine.getTaskService();
        //3.根据定义的key，负责人assignee来实现当前用户的任务列表查询
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("holiday")
                .taskAssignee("zhangsan")
                .list();
        //4.任务列表展示
        for (Task task : taskList) {
            System.out.println("流程实例id" + task.getProcessInstanceId());
            System.out.println("任务ID" + task.getId());
            System.out.println("负责人" + task.getAssignee());
            System.out.println("任务名称" + task.getName());
        }

    }

}
