package com.example.spring.hutool.masterworker;

public class EvalImpl implements Eval {
    @Override
    public <T> T eval() {
        return (T) "eval";
    }
}
