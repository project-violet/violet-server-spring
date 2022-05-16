package com.example.violetserver.service;

import com.example.violetserver.ApplicationContextProvider;
import com.example.violetserver.SpringConfigure;
import com.example.violetserver.config.RedisConfig;
import com.example.violetserver.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;


@Service
public class RedisMessageSubscriber implements MessageListener {

    public void onMessage(Message message, byte[] pattern) {
        String[] split = message.toString().split("-");

        String key = split[0];
        String articleId = split[1];

//        ZSetOperations<String, String> stringStringZSetOperations = stringRedisTemplate.opsForZSet();
//        stringStringZSetOperations.incrementScore(key, articleId,-1);

        System.out.println("Message received: " + message.toString());
    }
}