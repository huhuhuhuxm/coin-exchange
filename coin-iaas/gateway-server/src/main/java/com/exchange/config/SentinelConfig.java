package com.exchange.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * TODO 临时方案
 * 自定义逻辑处理被限流的请求
 * 为了解决sentinel和gateway在打到限流时候直接报错
 * 详情：https://github.com/alibaba/Sentinel/issues/3298
 */
@Configuration
public class SentinelConfig {
    public SentinelConfig() {
        GatewayCallbackManager.setBlockHandler(new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable ex) {
                return ServerResponse.ok().body(Mono.just("限流啦,请求太频繁"), String.class);
            }
        });
    }
}
