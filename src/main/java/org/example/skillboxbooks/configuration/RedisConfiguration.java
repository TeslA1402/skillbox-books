package org.example.skillboxbooks.configuration;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return builder -> builder.withCacheConfiguration(CacheNames.BOOKS_BY_AUTHOR_AND_NAME,
                RedisCacheConfiguration
                        .defaultCacheConfig()
                        .entryTtl(Duration.ofHours(1))
        );
    }
}
