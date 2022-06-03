package com.example.violetserver.service;

import com.example.violetserver.record.RecordService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;


//@RequiredArgsConstructor
@Service
public class RedisMessageSubscriber implements MessageListener {
    private final RecordService recordService;

    public RedisMessageSubscriber(RedisMessageListenerContainer redisMessageListenerContainer,
                                  RecordService recordService) {
        this.recordService = recordService;

        redisMessageListenerContainer.addMessageListener(this, new PatternTopic("__keyevent@*__:expired"));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String[] split = message.toString().split("-");

        String key = split[0];
        String articleId = split[1];

        recordService.expire(key, articleId);
    }
}