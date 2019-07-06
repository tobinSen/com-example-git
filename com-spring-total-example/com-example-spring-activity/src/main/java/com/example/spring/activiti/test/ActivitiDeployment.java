package com.example.spring.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;

/**
 * 流程定义部署 :本质就是将bpmn文件以activiti的方式插入到mysql中
 * deploy：act_re_deployment 部署信息
 * act_re_procdef           流程定义信息
 * act_ge_byteArray         png和bpmn
 * <p>
 * 流程定义(processDefinition)       流程部署(deployment)
 * -------->   activity三张表
 * <p>
 * 流程实例  (processInstance)
 * 流程定义好比是java中一个类
 * 流程实例好比是java中一个实例（对象）
 * <p>
 * 流程实例启动步骤：
 *  RepositoryService  --->流程部署  | 查询流程定义 | 删除流程定义(级联删除) | 获取到png和bpmn文件 |所有流程实例激活和挂起
 *  RuntimeService     --->流程实例  | 管理businessKey 业务表 | 流程实例激活和挂起
 *  TaskService        --->完成流程  | 查询流程任务
 *  HistoryService     --->历史信息查询
 */
public class ActivitiDeployment {

    public static void main(String[] args) {
        //1.创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println("processEngine-->" + processEngine);
        //2.得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.进行部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("diagram/holiday.bpmn")
                .addClasspathResource("diagram/holiday.png")
                .name("请假申请流程") //流程信息
                .deploy();
        //4.输出部署的一些信息
        System.out.println(deployment.getName());
        System.out.println(deployment.getId());


//        //zip文件方式进行部署
//
//        InputStream inputStream = ActivitiDeployment.class.getClassLoader().getResourceAsStream("diagram/holiday.zip");
//        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
//        Deployment zipDeployment = repositoryService.createDeployment()
//                .addZipInputStream(zipInputStream)
//                .name("请假申请流程")
//                .deploy();

    }
}
