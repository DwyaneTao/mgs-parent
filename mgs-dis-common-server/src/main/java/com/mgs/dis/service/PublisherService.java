package com.mgs.dis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PublisherService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String sendMessage(String channelName, String message){
        try{
            stringRedisTemplate.convertAndSend(channelName, message);
            log.info("发送成功消息:" + message);
            return "SUCCESS!";
        }catch (Exception e){
            log.error("发送消息失败：" + message);
            return "ERROR";
        }
    }
}
