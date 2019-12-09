package com.example.spring.log4j;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class Log4jCongroller {

    private static Logger logger = Logger.getLogger(Log4jCongroller.class);

    @RequestMapping("log4j.do")
    public JSONObject log4j(String name) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("randomNum", new Random().nextInt(100));
        logger.info(jsonObject);
        return jsonObject;
    }

}
