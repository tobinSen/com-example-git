package com.example.spring.activiti;

import org.activiti.engine.RepositoryService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(locations = "classpath:activiti.cfg.xml")
public class ActivitiSpringIntergrationTest {

    @Autowired
    private RepositoryService repositoryService;

    public void test() {

    }
}
