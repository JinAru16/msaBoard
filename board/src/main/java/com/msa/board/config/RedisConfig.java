package com.msa.board.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Value("${spring.data.redis.auth.database}")
    private int authRedisIndex;

    @Value("${spring.data.redis.board.database}")
    private int boardRedisIndex;

    @Value("${spring.data.redis.board.host}")
    private String boardRedisHost;

    @Value("${spring.data.redis.board.port}")
    private int boardRedisPort;

    @Value("${spring.data.redis.auth.host}")
    private String authHost;

    @Value("${spring.data.redis.board.host}")
    private String boardHost;


    @Bean(name = "blacklistRedisTemplate") // 블랙리스트 검증용
    public RedisTemplate<String, Object> blacklistRedisTemplate() {
        return createRedisTemplate(redisConnectionFactory, authRedisIndex); // ✅ 블랙리스트 검증은 database: 0 사용
    }

    // ✅ 게시글 캐싱용 CacheManager
    @Bean(name = "boardCacheManager")
    public RedisCacheManager boardCacheManager() {
        // ✅ ObjectMapper 설정 (LocalDateTime 지원 추가)
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // ✅ JavaTimeModule 추가
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        // ✅ 🚀 board-server에서 사용할 RedisConnectionFactory 생성
        LettuceConnectionFactory boardRedisFactory = new LettuceConnectionFactory(new RedisStandaloneConfiguration(boardRedisHost, boardRedisPort));
        boardRedisFactory.setDatabase(boardRedisIndex); // ✅ board-server에서 사용할 DB Index
        boardRedisFactory.afterPropertiesSet(); // 🚨 꼭 초기화 필요!

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)) // ✅ TTL 10분
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)));

        return RedisCacheManager.builder(boardRedisFactory) // ✅ 여기에서 boardRedisFactory 사용
                .cacheDefaults(config)
                .build();
    }

    private RedisTemplate<String, Object> createRedisTemplate(RedisConnectionFactory redisConnectionFactory, int databaseIndex) {
        // ✅ 기존 주입된 redisConnectionFactory 활용 (새로운 Factory 생성 X)
        LettuceConnectionFactory factory = (LettuceConnectionFactory) redisConnectionFactory;
        factory.setDatabase(databaseIndex); // ✅ 데이터베이스 Index만 변경 (새로운 Factory 생성 X)
        factory.afterPropertiesSet(); // ✅ 초기화 수행

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // ✅ ObjectMapper 설정 (LocalDateTime 지원 추가)
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 🔥 LocalDateTime 지원

        // ✅ LinkedHashMap -> 지정한
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);

        redisTemplate.setConnectionFactory(factory); // ✅ 기존 Factory 활용
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        // ✅ Value Serializer 설정 (JSON 변환)
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));

        return redisTemplate;
    }
}