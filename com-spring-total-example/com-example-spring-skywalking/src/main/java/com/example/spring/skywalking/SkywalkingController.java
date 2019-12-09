package com.example.spring.skywalking;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@RestController
public class SkywalkingController {

    @RequestMapping("skywalking.do")
    public JSONObject skywalking(String name) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("randomNum", new Random().nextInt(100));
        //jsonObject.put("traceId", TraceContext.traceId());
        jsonObject.put("item", build());
        return jsonObject;
    }

    private JSONArray build() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < 10; i++) {
            JSONObject json = new JSONObject();
            json.put("id", i);
            json.put("timeStamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            array.add(json);
        }
        return array;
    }

}
