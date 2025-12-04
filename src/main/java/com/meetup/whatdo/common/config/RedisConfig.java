package com.meetup.whatdo.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * TODO: Deprecated 메서드 수정
 * Redis Bean 설정 클래스
 * @version 1.0
 * @author Minchan Park
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // ObjectMapper 커스터마이징 (DateTime, Kotlin, Default Typing 등)
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.registerModule(new JavaTimeModule());
        //objectMapper.activateDefaultTyping(
                //LaissezFaireSubTypeValidator.instance,
                //ObjectMapper.DefaultTyping.NON_FINAL
        //);

        // 새로운 JsonRedisSerializer 사용
        //JsonRedisSerializer<Object> serializer = new JsonRedisSerializer<>(Object.class);
        //serializer.setObjectMapper(objectMapper);

        // key / value 직렬화 설정
        template.setKeySerializer(new StringRedisSerializer());
        //template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        //template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }

}
