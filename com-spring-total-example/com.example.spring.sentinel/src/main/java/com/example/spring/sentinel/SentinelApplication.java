package com.example.spring.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelApplication.class, args);
    }

    /**
     *                                    ProcessorSlot
     *
     *                                  AbstractLinkedProcessorSlot  (next)
     *
     *         ProcessorSlotChain           NodeSelectorSlot  ClusterBuilderSlot  LogSlot  StatisticSlot  SystemSlot  AuthoritySlot  FlowSlot  DegradeSlot
     *                                         (DefaultNode)     (ClusterNode)
     *      DefaultProcessorSlotChain (first  <--> end)
     *
     *
     *  first -> NodeSelectorSlot ->  ClusterBuilderSlot  -> LogSlot -> StatisticSlot -> SystemSlot -> AuthoritySlot -> FlowSlot -> DegradeSlot -> end
     *
     *
     * //总结： 5 8 3 点
     */
}
