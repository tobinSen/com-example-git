package com.example.spring.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

/**
 * 任务完成
 * <p>
 * 影响表：
 * act_hi_actinst
 * act_hi_identitylink 参与者
 * act_hi_taskinst  任务实例
 * act_ru_execution
 * act_ru_identitylink 参与者
 * act_ru_task  只有一条记录，一个节点一条任务
 */
public class ActivitiCompleteTask {
    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到taskService对象
        TaskService taskService = processEngine.getTaskService();

        //3.处理任务，结合当前用户任务列表的查询操作，任务Id:2505
        //taskService.complete("2505");


        //查询并完成任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holiday")
                .taskAssignee("wangwu")
                .singleResult();
        System.out.println("任务Id:" + task.getId());
        taskService.complete(task.getId());
    }

}
