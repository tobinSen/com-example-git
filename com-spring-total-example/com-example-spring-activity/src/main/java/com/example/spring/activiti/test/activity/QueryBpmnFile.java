package com.example.spring.activiti.test.activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 读取bpmn和png文件，保存到磁盘下
 * <p>
 * 用户查看请假流程：
 * <p>
 * 1.activiti的api实现
 * 2.可以使用jdbc的blob类型，clob的读取
 * 3.Io流程转换 通过common-io.jar
 */
public class QueryBpmnFile {

    public static void main(String[] args) throws Exception {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        ProcessDefinition processDefinition = processDefinitionQuery.processDefinitionKey("holiday").singleResult();

        String deploymentId = processDefinition.getDeploymentId();

        //参数一：部署Id， 参数二：资源名称
        //processDefinition.getDiagramResourceName()图片资源的名称
        InputStream pngIs = repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());

        //processDefinition.getResourceName()获取bpmn文件名称
        InputStream bpmnIs = repositoryService.getResourceAsStream(deploymentId, processDefinition.getResourceName());

        OutputStream pngOs = new FileOutputStream("D:\\user");

        IOUtils.copy(pngIs, pngOs);
        IOUtils.closeQuietly(pngIs);
        IOUtils.closeQuietly(pngOs);

    }
}
