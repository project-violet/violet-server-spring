package com.example.violetserver.record;

import com.example.violetserver.record.entity.JpaViewReportRepository;
import com.example.violetserver.record.entity.JpaViewTimeRepository;
import com.example.violetserver.record.entity.ViewReport;
import com.example.violetserver.record.entity.ViewTime;
import com.example.violetserver.record.model.TopRecentModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordService {

    private final StringRedisTemplate redisTemplate;
    private final JpaViewTimeRepository viewTimeRepository;
    private final JpaViewReportRepository viewReportRepository;

    private final RecordRepository recordRepository;

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

    public void insertViewReport(RecordDto.ViewReportRequest dto) {
        ViewReport viewReport = ViewReport
                .builder()
                .ArticleId((long) dto.getId())
                .StartsTime(Date.from(Instant.ofEpochMilli(dto.getStartsTime())))
                .EndsTime(Date.from(Instant.ofEpochMilli(dto.getEndsTime())))
                .LastPage((long) dto.getLastPage())
                .ValidSeconds((long) dto.getValidSeconds())
                .Pages((long) dto.getPages())
                .MsPerPages(dto.getMsPerPages())
                .UserAppId(dto.getUser())
                .TimeStamp(Date.from(Instant.now()))
                .build();
        viewReportRepository.save(viewReport);
    }

    public Double view(int articleId) {
        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
        return stringStringZSetOperations.score("alltime", String.valueOf(articleId));
    }

    public void expire(String key, String articleId) {
        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
        stringStringZSetOperations.incrementScore(key, articleId,-1);
    }

    public List<List<Long>> top(RecordDto.TopRequest dto) {
        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
        String type = dto.getType();

        if (type.equals("week"))
            type = "weekly";
        else if (type.equals("month"))
            type = "monthly";

        List<List<Long>> result = new ArrayList<>();

        var query = stringStringZSetOperations.reverseRangeWithScores(type, dto.getOffset(), dto.getCount());

        for (ZSetOperations.TypedTuple<String> stringTypedTuple : query) {
            List<Long> e = new ArrayList<>();
            e.add(Long.valueOf(stringTypedTuple.getValue()));
            e.add(stringTypedTuple.getScore().longValue());
            result.add(e);
        }

        return result;
    }

    public Long topTs(int s) {
        return recordRepository.queryTopTs(s);
    }

    public List<List<Long>> topRecent(int s) {
        var query = viewTimeRepository.findTopRecent(s);
        List<List<Long>> result = new ArrayList<>();
        for (TopRecentModel topRecentModel : query) {
            List<Long> e = new ArrayList<>();
            e.add((long) topRecentModel.getArticleId());
            e.add((long) topRecentModel.getC());
            result.add(e);
        }
        return result;
    }
}
