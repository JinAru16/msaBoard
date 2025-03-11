package com.msa.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Value("${spring.data.redis.database}")
    private int boardRedisIndex;

    @Bean(name = "boardCacheRedisTemplate") // 게시글 캐싱용
    public RedisTemplate<String, Object> boardCacheRedisTemplate() {
        return createRedisTemplate(boardRedisIndex); // ✅ 게시글 캐싱은 database: 1 사용
    }

    @Bean(name = "blacklistRedisTemplate") // 블랙리스트 검증용
    public RedisTemplate<String, Object> blacklistRedisTemplate() {
        return createRedisTemplate(0); // ✅ 블랙리스트 검증은 database: 0 사용
    }

    private RedisTemplate<String, Object> createRedisTemplate(int databaseIndex) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        LettuceConnectionFactory factory = (LettuceConnectionFactory) redisConnectionFactory;
        config.setHostName(factory.getHostName());
        config.setPort(factory.getPort());
        config.setDatabase(databaseIndex);
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(config);
        connectionFactory.afterPropertiesSet();

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}