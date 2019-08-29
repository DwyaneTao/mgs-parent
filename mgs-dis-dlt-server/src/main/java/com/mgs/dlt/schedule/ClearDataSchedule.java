package com.mgs.dlt.schedule;

import com.mgs.dlt.domain.DltInterfaceLogPO;
import com.mgs.dlt.mapper.DltInterfaceLogMapper;
import com.mgs.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 *   2018/5/21.
 */

@Component
@Lazy(false)
@Configuration
@EnableScheduling
public class ClearDataSchedule {

    private static final Logger LOG = LoggerFactory.getLogger(BatchPushRoomDataSchedule.class);
    private static final String CRON_INTERFACE_LOG_CLEAR = "0 0 0/5 * * ?";
    private static final String CRON_ORDER_CODE_CONFIG_CLEAR = "0 0 4 * * ?";

    @Autowired
    private DltInterfaceLogMapper interfaceLogMapper;

    @Scheduled(cron = CRON_INTERFACE_LOG_CLEAR)
    public void clearInterfaceLogData() {
        try {
            LOG.info("清理接口日志表前3天的数据开始...");
            Example example = new Example(DltInterfaceLogPO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andLessThan("createdDt", DateUtil.getDate(new Date(), -3, 0));
            interfaceLogMapper.deleteByExample(example);
            LOG.info("清理接口日志表前3天的数据结束...");
        } catch (Exception e) {
            LOG.error("清理接口日志失败", e);
        }
    }

//    @Scheduled(cron = CRON_ORDER_CODE_CONFIG_CLEAR)
//    public void clearOrderCodeConfigData() {
//
//    }

}
