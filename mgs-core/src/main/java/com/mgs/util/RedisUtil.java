package com.mgs.util;

import com.mgs.enums.IncrementTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by ASUS on 2018/8/2.
 */
@Component
@Slf4j
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 保存缓存到redis中
     *
     * @param key
     * @param value
     * @param second
     * @return
     */
    public boolean saveDataInCache(String key, String value, Integer second) {
        try {
            stringRedisTemplate.opsForValue().set(key, value, second, TimeUnit.SECONDS);
            log.info("saveDataInCache has success,key:" + key + ",value:" + value);
            return true;
        } catch (Exception e) {
            log.error("saveDataInCache has error", e);
        }
        return false;
    }

    /**
     * 保存缓存到redis中
     *
     * @param key
     * @param value
     * @return
     */
    public boolean saveDataInCache(String key, String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            log.info("saveDataInCache has success,key:" + key + ",value:" + value);
            return true;
        } catch (Exception e) {
            log.error("saveDataInCache has error", e);
        }
        return false;
    }

    public String queryDataInCache(String key) {
        try {
            String s = stringRedisTemplate.opsForValue().get(key);
            return s;
        } catch (Exception e) {
            log.error("queryDataInCache has error", e);
        }
        return null;
    }

    /**
     * 从缓存中删除指定key
     *
     * @param key
     * @return
     */
    public boolean delDataInCache(String key) {
        try {
            Boolean result = stringRedisTemplate.delete(key);
            log.info("delDataInCache has success,key:" + key);
            if (result != null) {
                return result;
            }
        } catch (Exception e) {
            log.error("delDataInCache has error", e);
        }
        return false;
    }

    /**
     * 非阻塞查询符合给定模式的key
     *
     * @param keyPattern
     * @param count
     * @return
     */
    public Set<String> scanDataInCache(String keyPattern, Integer count) {
        try {
            Set<String> set = stringRedisTemplate.execute((RedisCallback<Set<String>>) connection -> {
                Set<String> binaryKeys = new HashSet<>();
                Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(keyPattern).count(count).build());
                while (cursor.hasNext()) {
                    binaryKeys.add(new String(cursor.next()));
                }
                return binaryKeys;
            });
            return set;
        } catch (Exception e) {
            log.error("scanDataInCache has error", e);
        }
        return null;
    }

    public String hmset(String key, String field, Object object){
        try{
            stringRedisTemplate.opsForHash().put(key, field, object);
        }catch (Exception e){
            log.error("hmset has error", e);
            return "HMSET ERROR";
        }
        return "HMSET SUCCESS";
    }

    public  String hmset(String key, Map<String, String> map){
        try{
            stringRedisTemplate.opsForHash().putAll(key, map);
        }catch (Exception e){
            log.error("hmset has error", e);
            return "HMSET ERROR";
        }
        return "HMSET SUCCESS";
    }

    public Map<Object, Object> hmget(String key){
        Map<Object, Object> map = new HashMap<Object, Object>();
        try{
           map = stringRedisTemplate.opsForHash().entries(key);
        }catch (Exception e){
            log.error("hmget has error", e);
        }
        return map;
    }

    public Object hmget(String key, String fields){
        Object object = null;
        try{
            object = stringRedisTemplate.opsForHash().get(key, fields);
        }catch (Exception e){
            log.error("hmget has error", e);
        }
        return object;
    }

    public Long rpush(String key, Collection<String> value) {
        return stringRedisTemplate.opsForList().rightPushAll(key, value);
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    public Long hdel(String key, Object... fields){
        return stringRedisTemplate.opsForHash().delete(key, fields);
    }

    /***
     * 获取list列表的值，并且取出
     * @param key
     * @param end 默认从0开始，读取多少条数据
     */
    public List<Object> getAndDeleteList(String key,final int end){
        List<Object> list = stringRedisTemplate.executePipelined(new RedisCallback<String>(){
            // 自定义序列化
            RedisSerializer keyS = stringRedisTemplate.getKeySerializer();
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                for(int i = 0; i < end; i++){
                    keyS.deserialize(redisConnection.lPop(key.getBytes()));
                }
                return null;
            }
        });
        return list;
    }
}
