package com.example.spring.log4j2;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Random;

@RestController
public class Log4j2Controller {

    private final static Logger log = LoggerFactory.getLogger(Log4j2Controller.class);


    @Autowired
    PushServiceImpl pushService;

    @RequestMapping("log4j2.do")
    public JSONObject log4j2(String name) throws Exception {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("random", new Random().nextInt(1000));
        log.info(json.toJSONString());
        return json;
    }

    @RequestMapping("/getData")
    @ResponseBody
    public DeferredResult<String> getPushData() {
        return pushService.getDeferredResult();
    }

}
