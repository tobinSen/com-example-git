package com.example.spring.activiti.test.activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;

import java.util.List;

/**
 * 流程定义查询
 */
public class ActivitiRepository {

    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.创建RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.得到ProcessDefinitionQuery对象
        //就是一个查询器
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        //4.设置条件，并查询出当前的所有流程定义(流程定义key ： holiday)
        List<ProcessDefinition> list = processDefinitionQuery.processDefinitionKey("holiday")
                .orderByProcessDefinitionVersion()//排序方式，定义的版本号
                .desc().list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println(processDefinition.getId());
            System.out.println(processDefinition.getVersion());
            System.out.println(processDefinition.getDeploymentId());
            System.out.println(processDefinition.getName());
            System.out.println(processDefinition.getKey());
        }


    }
}
