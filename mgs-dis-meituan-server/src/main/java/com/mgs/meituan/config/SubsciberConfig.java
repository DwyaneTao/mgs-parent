package com.mgs.meituan.config;


import com.mgs.meituan.listener.MeituanReceiver;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@AutoConfigureAfter({MeituanReceiver.class})
public class SubsciberConfig {

    /**
     * 消息监听配置器，注入接受消息方法，输入方法名字
     * @param meituanReceiver
     * @return
     */
    @Bean
    public MessageListenerAdapter getMessageListenerAdaptor(MeituanReceiver meituanReceiver){
        return new MessageListenerAdapter(meituanReceiver);
    }

    /**
     * 创建消息监听容器
     * @param redisConnectionFactory
     * @param messageListenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer getRedisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter){
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, new PatternTopic("increment"));
        return redisMessageListenerContainer;
    }
}
