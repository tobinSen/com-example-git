package com.example.spring.mdc.controller;

import com.example.spring.mdc.common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MDCController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("mdc")
    public Response mdc() throws Exception {
        //MDC.put("X-B3-TraceId", UUID.randomUUID().toString());
        logger.info("MDCController");
        return new Response();
    }
}
