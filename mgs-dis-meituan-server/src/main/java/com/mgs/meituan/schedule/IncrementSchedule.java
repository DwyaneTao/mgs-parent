package com.mgs.meituan.schedule;

import com.mgs.keys.RedisKey;
import com.mgs.meituan.config.RequestConfig;
import com.mgs.meituan.key.MeituanRedisKey;
import com.mgs.meituan.service.IncrementService;
import com.mgs.util.DateUtil;
import com.mgs.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class IncrementSchedule {

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private RequestConfig requestConfig;

    @Autowired
    private IncrementService incrementService;

    /**
     * 处理增量的定时任务
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void handlerIncrement(){
      /*  log.info("处理增量的定时任务运行成功");
        List<Object> incrementList = redisUtil.getAndDeleteList(MeituanRedisKey.meituanIncrementKey, requestConfig.getIncrementNumber());


        if(!CollectionUtils.isNotEmpty(incrementList)){
            try{
                incrementService.pullIncrement(incrementList);
            }catch (Exception e){
                log.error("增量处理失败");
            }
        }else {
            log.info("没有需要处理的增量信息");
        }
        log.info("增量处理成功");*/
    }
}
