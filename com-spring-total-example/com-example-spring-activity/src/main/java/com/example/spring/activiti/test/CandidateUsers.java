package com.example.spring.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * 候选人 (任务中没有assigness)
 * 多个候选人之间用逗号隔开
 * <p>
 * 办理组任务
 * 1.查询组任务
 * 2.拾取任务
 * 3.查询个人任务
 * 4.办理个人任务
 */
public class CandidateUsers {

    public static void main(String[] args) {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        TaskService taskService = processEngine.getTaskService();

        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("holiday")
                .taskCandidateUser("zhangsan")//作为一个候选人，看有没有任务
                .list();
        //拾取任务
        Task task = taskService.createTaskQuery()
                .taskId("1001")
                .taskCandidateUser("zhangsan")
                .singleResult();

        if (null != task) {
            //候选人是针对节点任务的
            taskService.claim("1001", "zhangsan"); //由候选人--》负责人
        }

        //归还组任务（负责人-->null）
        taskService.setAssignee("122", null);
        //任务交接：任务负责人--》候选人
        taskService.setAssignee("123", "lisi");//交接任务，本质就是候选人拾取任务

    }

}
