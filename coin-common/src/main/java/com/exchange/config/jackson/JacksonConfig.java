package com.exchange.config.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/16 14:14
 */
@Configuration
public class JacksonConfig {

    /**
     * 配置ObjectMapper
     * @return
     */
    @Bean
    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        objectMapper.registerModule(simpleModule);
//        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/shanghai"));
//        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//
//        return objectMapper;

        ObjectMapper objectMapper = new ObjectMapper();

        // 配置 DateTimeFormatter 格式化 LocalDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 配置 JavaTimeModule
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        objectMapper.registerModule(javaTimeModule);

        // 禁止将日期序列化为时间戳
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 防止前端 Long 精度丢失
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        // 设置 JSON 可见性
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 设置时区，仅对旧版 Date 类型生效（与 LocalDateTime 无关）
        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        return objectMapper;
    }
}
