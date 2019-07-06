package com.uplooking.javassit;

import java.lang.instrument.Instrumentation;

public class PerfMonAgent {
    private static Instrumentation inst = null;

    public static void premain(String agentArgs, Instrumentation _inst) {
        System.out.println("PerfMonAgent.premain() was called...");
        inst = _inst;
        System.out.println("Adding a PerfMonXformer instance to the JVM...");
        inst.addTransformer(new PerfMonXformer());
    }

}
