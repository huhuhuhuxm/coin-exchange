package com.exchange.filter;

import com.exchange.properties.UrlWhiteListProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * jwt校验过滤器
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/14 12:54
 */
@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtCheckFilter implements GlobalFilter, Ordered {

    StringRedisTemplate stringRedisTemplate;
    UrlWhiteListProperties urlWhiteListProperties;

    /**
     * 对请求所携带的Token做校验
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.该接口是否需要token才能访问
        if (!isRequireToken(exchange)) {
            return chain.filter(exchange);
        }
        // 2.取出用户token
        String token = getUserToken(exchange);
        // 3.判断用户token是有效
        if (!StringUtils.hasText(token)) {
            return authorizationErrorResult(exchange);
        }
        // redis中维护的是黑名单，token在黑名单中匹配成功则抛出异常
        Boolean hasKey = stringRedisTemplate.hasKey(token);
        if (hasKey!=null && hasKey) {
            return authorizationErrorResult(exchange);
        }

        return chain.filter(exchange);
    }

    /**
     * 给用户响应一个没有token的错误
     * @param exchange
     * @return
     */
    private Mono<Void> authorizationErrorResult(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();
        // 创建json对象
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("error", "NoAuthorization");
        jsonObject.put("errorMsg", "Token is Null or Error");
        // 将对象转换成字符串
        String jsonResponse = null;
        try {
            jsonResponse = objectMapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer wrap = response.bufferFactory().wrap(jsonResponse.getBytes());
        return response.writeWith(Mono.just(wrap));
    }

    /**
     * 获取用户的token
     * @param exchange
     * @return
     */
    private String getUserToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(token)) {
            return null;
        }
        return token.replace("bearer", "");
    }


    // 判断请求是否需要携带token才可访问
    private boolean isRequireToken(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        if (urlWhiteListProperties.getUrlWhiteList().contains(path)) {
            return false;
        }
        return true;
    }

    /**
     * 过滤器顺序
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
