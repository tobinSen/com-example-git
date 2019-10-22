package com.example.spring.mockmvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockmvcController {

    @RequestMapping("/mock.do")
    public String mockMvc(String name) throws Exception {
        return name;
    }

}
