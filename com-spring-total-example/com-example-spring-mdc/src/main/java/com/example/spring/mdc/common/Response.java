package com.example.spring.mdc.common;

import org.slf4j.MDC;

import java.io.Serializable;

public class Response<T> implements Serializable {

    private int code;
    private String message = "成功";
    private String traceId;
    private T result;

    public Response() {
        this.traceId = MDC.get("X-B3-TraceId");
    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
        this.traceId = MDC.get("X-B3-TraceId");
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
