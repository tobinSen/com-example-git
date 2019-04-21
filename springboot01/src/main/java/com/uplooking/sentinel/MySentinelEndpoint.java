package com.uplooking.sentinel;


import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.google.common.collect.Lists;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySentinelEndpoint extends AbstractEndpoint<Map<String, Object>> {


    public MySentinelEndpoint() {
        super("sentinel-service");//这里是端点检查的url访问点
    }

    @Override
    public Map<String, Object> invoke() {
        Map<String, Object> result = new HashMap<>();
        List<FlowRule> flowRules = FlowRuleManager.getRules();
        List<DegradeRule> degradeRules = DegradeRuleManager.getRules();
        List<SystemRule> systemRules = SystemRuleManager.getRules();
        List<AuthorityRule> authorityRules = AuthorityRuleManager.getRules();
        result.put("FlowRules", flowRules);
        result.put("DegradeRules", degradeRules);
        result.put("SystemRules", systemRules);
        result.put("AuthorityRules", authorityRules);
        return result;
    }

    public void flowRule() {
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("wms-web");//对那个资源限流
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);//默认对QPS进行限流
        flowRule.setCount(20);//限流阀值
        flowRule.setLimitApp("default");//是否根据【调用者】来限流
        flowRule.setStrategy(RuleConstant.STRATEGY_DIRECT);//直接拒绝
        flowRule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);//超过阀值后，默认直接拒绝
        FlowRuleManager.loadRules(Lists.newArrayList(flowRule));//加载流控规则
    }

    public void degradeRule() {
        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource("wms-web");
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);//RT方式进行熔断
        degradeRule.setCount(20);//响应时间
        degradeRule.setTimeWindow(5);//在时间窗口内，都不允许访问
        DegradeRuleManager.loadRules(Lists.newArrayList(degradeRule));//加载服务熔断规则
    }

    public void authorityRule() {
        AuthorityRule authorityRule = new AuthorityRule();
        authorityRule.setResource("wms-web");
        authorityRule.setStrategy(RuleConstant.AUTHORITY_BLACK);
        authorityRule.setLimitApp("appA,appB");//调用这设置黑名单
        AuthorityRuleManager.loadRules(Lists.newArrayList(authorityRule));
    }

    public void systemRule() {
        SystemRule systemRule = new SystemRule();
        systemRule.setResource("wms-web");
        systemRule.setQps(100);
        systemRule.setAvgRt(10000);
        systemRule.setHighestSystemLoad(10000);
        systemRule.setMaxThread(1000);
        SystemRuleManager.loadRules(Lists.newArrayList(systemRule));
    }
}
