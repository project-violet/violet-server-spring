package com.example.violetserver.record;

import com.example.violetserver.record.RecordDto;
import com.example.violetserver.record.entity.JpaViewTimeRepository;
import com.example.violetserver.record.entity.ViewTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class RecordService {

    private final StringRedisTemplate redisTemplate;
    private final JpaViewTimeRepository viewTimeRepository;

    public void insertView(RecordDto.ViewCloseRequest dto) {
        LocalDateTime now = LocalDateTime.now();

        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
        stringStringZSetOperations.incrementScore("alltime", String.valueOf(dto.getNo()), 1);
        stringStringZSetOperations.incrementScore("daily", String.valueOf(dto.getNo()), 1);
        stringStringZSetOperations.incrementScore("weekly", String.valueOf(dto.getNo()), 1);
        stringStringZSetOperations.incrementScore("monthly", String.valueOf(dto.getNo()), 1);

        String keyName =  String.valueOf(dto.getNo()) + "-" + now.toString();

        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        stringStringValueOperations.set("daily-" + keyName, "1", Duration.ofDays(1));
        stringStringValueOperations.set("weekly-" + keyName, "1", Duration.ofDays(7));
        stringStringValueOperations.set("monthly-" + keyName, "1", Duration.ofDays(30));

        ViewTime viewTime = ViewTime
                .builder()
                .TimeStamp(Date.from(Instant.now()))
                .ArticleId((long) dto.getNo())
                .ViewSeconds((long) dto.getTime())
                .UserAppId(dto.getUser())
                .build();
        viewTimeRepository.save(viewTime);
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
