package com.exchange.controller;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 网关限流规则
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/2 21:45
 */
@RestController
@RequestMapping("/gw")
public class GatewayFlowRulesController {

    /**
     * 获取当前系统的限流策略
     */
    @GetMapping("/flow/rules")
    public Set<GatewayFlowRule> getCurrentGatewayFlowRules() {
        return GatewayRuleManager.getRules();
    }

    /**
     * 获取自定义的api分组
     */
    @GetMapping("/api/groups")
    public Set<ApiDefinition> getCurrentApiDefinition() {
        return GatewayApiDefinitionManager.getApiDefinitions();
    }
}
