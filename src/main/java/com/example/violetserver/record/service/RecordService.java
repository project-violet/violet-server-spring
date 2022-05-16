package com.example.violetserver.record.service;

import com.example.violetserver.record.dto.RecordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class RecordService {

    private final StringRedisTemplate redisTemplate;

    public void insertView(RecordDto.ViewCloseRequest dto) {
        LocalDateTime now = LocalDateTime.now();

        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
        stringStringZSetOperations.incrementScore("alltime", String.valueOf(dto.getNo()), 1);
        stringStringZSetOperations.incrementScore("daily", String.valueOf(dto.getNo()), 1);
        stringStringZSetOperations.incrementScore("weekly", String.valueOf(dto.getNo()), 1);
        stringStringZSetOperations.incrementScore("monthly", String.valueOf(dto.getNo()), 1);

        String keyName =  String.valueOf(dto.getNo()) + "-" + now.toString();

        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        stringStringValueOperations.set("test-" + keyName, "1", Duration.ofSeconds(1));
        stringStringValueOperations.set("daily-" + keyName, "1", Duration.ofDays(1));
        stringStringValueOperations.set("weekly-" + keyName, "1", Duration.ofDays(7));
        stringStringValueOperations.set("monthly-" + keyName, "1", Duration.ofDays(30));
    }

    public Double view(int articleId) {
        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
        return stringStringZSetOperations.score("alltime", String.valueOf(articleId));
    }

    public void expire(String key, String articleId) {
        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
        stringStringZSetOperations.incrementScore(key, articleId,-1);

    }
}
