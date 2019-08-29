package com.mgs.meituan.listener;

import com.alibaba.fastjson.JSON;
import com.mgs.dis.dto.IncrementDTO;
import com.mgs.enums.ChannelEnum;
import com.mgs.meituan.key.MeituanRedisKey;
import com.mgs.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@Slf4j
public class MeituanReceiver implements MessageListener {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        RedisSerializer<String> valueSerializer = redisTemplate.getStringSerializer();
        String deserialize = valueSerializer.deserialize(message.getBody());

        //TODO 存入redis中
        addMessageToRedis(deserialize);
    }

    //将信息存入redis中去
    private void addMessageToRedis(String message){
        IncrementDTO incrementDTO = JSON.parseObject(message, IncrementDTO.class);
        if(incrementDTO.getChannelCode() != null && !incrementDTO.getChannelCode().equals(ChannelEnum.MEITUAN)){
            return ;
        }
        redisUtil.rpush(MeituanRedisKey.meituanIncrementKey, new ArrayList<String>(Arrays.asList(message)));
        log.info("美团增量为" + message);
    }
}
