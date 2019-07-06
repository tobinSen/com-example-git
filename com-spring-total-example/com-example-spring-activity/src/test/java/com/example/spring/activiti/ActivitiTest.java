package com.example.spring.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 * 25张表
 */
public class ActivitiTest {


    @Test
    public void genTest() {

        //方式1
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.
                createProcessEngineConfigurationFromResource("activiti.cfg.xml");

        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println(processEngine);

        //方式2
        /**
         * 条件：1.activiti.cfg.xml
         *      2.bean id = processEngineConfiguration
         */
//        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
//        System.out.println(defaultProcessEngine);
    }
}
