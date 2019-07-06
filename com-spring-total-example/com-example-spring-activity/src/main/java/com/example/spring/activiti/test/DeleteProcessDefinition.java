package com.example.spring.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;

/**
 * 流程定义删除
        act_ge_byearray
        act_re_deployment   流程部署信息
        act_re_procdef   流程定义信息

 注意
    1.当正在执行的这一套流程，未完全执行完成，如果定义信息删除，就会失败，这个时候是无法删除的
    2.如果强制性删除？
     //级联删除
     //repositoryService.deleteDeployment("1", true);
    true:先删除未完成的节点信息，在删除流程定义信息

 */
public class DeleteProcessDefinition {

    //ru的表，流程跑完了，就没有了清空

    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.创建RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.删除流程定义 流程部署id
        repositoryService.deleteDeployment("1");
        //级联删除
        //repositoryService.deleteDeployment("1", true);

    }
}
