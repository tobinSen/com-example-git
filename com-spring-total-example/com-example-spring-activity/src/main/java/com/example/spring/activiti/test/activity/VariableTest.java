package com.example.spring.activiti.test.activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程变量 （全局Global variable）
 * 1.启动流程实例的时候设置
 * 2.任务办理complete时候设置
 * 3.通过当前流程实例
 * 4.通过当前任务设置
 * <p>
 * local局部变量
 * 当前运行的流程实例只能在该任务结束前使用，任务结束该变量无法在当前流程实例使用，可以通过查询历史任务查询
 */
public class VariableTest {

    public static void main(String[] args) {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        //方式1.流程定义key myProcess1
        Map<String, Object> map = new HashMap<>();
        Holiday holiday = new Holiday();
        map.put("holiday", holiday);
        runtimeService.startProcessInstanceByKey("myProcess1", map);

        //方式2
        TaskService taskService = processEngine.getTaskService();
        taskService.complete("1501", map);

        //方式3.
        //参数1.流程实例Id 参数2.流程变量名 参数3 流程变量值
        runtimeService.setVariable("2081", "holiday", holiday);

        //方式4. 绑定到任务节点上面
        taskService.setVariable("2011", "holiday", holiday);

        //local variable
        //设置作用域local变量，每个节点的名字可以相同，节点互不影响
        taskService.setVariablesLocal("2011", map);
    }
}
