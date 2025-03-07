package com.msa.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Value("${spring.data.redis.database}")
    private int boardRedisIndex;

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(connectionFactory(boardRedisIndex));
        // ✅ key-value를 문자열로 저장하도록 설정 (JSON 직렬화 방식도 가능)
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

    private RedisConnectionFactory connectionFactory(int redisIndex) {
        LettuceConnectionFactory factory = (LettuceConnectionFactory) redisConnectionFactory;
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(factory.getHostName());
        config.setPort(factory.getPort());
        config.setDatabase(redisIndex);
        return new LettuceConnectionFactory(config);
    }
}