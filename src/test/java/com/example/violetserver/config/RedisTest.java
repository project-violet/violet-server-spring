package com.example.violetserver.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class RedisTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @BeforeAll
    public static void beforeAll(@Autowired StringRedisTemplate redisTemplate) {
        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
        stringStringZSetOperations.remove("test", "1234");
    }

    @Test
    public void ZINCRBY_테스트() {
        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
        stringStringZSetOperations.incrementScore("test", "1234", 1);

        assertThat(stringStringZSetOperations.score("test", "1234")).isEqualTo(1);
    }

}
