package com.mgs.dlt.schedule;


import com.mgs.common.Response;
import com.mgs.dis.dto.DisMappingQueryDTO;
import com.mgs.dis.dto.DisProductMappingDTO;
import com.mgs.dis.remote.DisMappingRemote;
import com.mgs.common.constant.InitData;
import com.mgs.dlt.service.BatchPushRoomDataService;
import com.mgs.keys.ChannelCodeKey;
import com.mgs.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 定时推送房态、房价、房量信息到代理通
 *   2018/4/11.
 */
@Component
@Lazy(false)
@Configuration
@EnableScheduling
public class BatchPushRoomDataSchedule {

    private static final Logger LOG = LoggerFactory.getLogger(BatchPushRoomDataSchedule.class);
    private static final String CRON = "0 0/5 * * * ?";
    public static volatile Boolean needSyncRoomDataToDlt = true;

    @Autowired
    private DisMappingRemote disMappingRemote;

    @Autowired
    private BatchPushRoomDataService batchPushRoomDataService;

    @Scheduled(cron = CRON)
    public void batchPushRoomData() {
        LOG.info("batchPushRoomData execute... " + CRON);
        if (!needSyncRoomDataToDlt) {
            LOG.error("被设置为不需要同步报价到代理通，将直接在代理通后台录入报价");
            return;
        }

        // 查询房型映射表，查询所有已经映射的房型
        DisMappingQueryDTO disMappingQueryDTO = new DisMappingQueryDTO();
        disMappingQueryDTO.setDistributor(ChannelCodeKey.ctripCode);
        List<DisProductMappingDTO> disProductMappingDTOList = disMappingRemote.queryProductMapping(disMappingQueryDTO);

        if (CollectionUtils.isEmpty(disProductMappingDTOList)) {
            LOG.error("未查询到任何有映射关系的房型，推送结束");
            return;
        }

        //将映射关系按商家分组，分开推送
        Map<String,List<DisProductMappingDTO>> mappingMap = new HashMap<String,List<DisProductMappingDTO>>();
        if(null!= InitData.channelConfigMap && null!=InitData.channelConfigMap.keySet()) {
            for(String merchantCode : InitData.channelConfigMap.keySet()) {
                List<DisProductMappingDTO> mapRoomPOList = new ArrayList<>();
                mappingMap.put(merchantCode,mapRoomPOList);
            }

            for(DisProductMappingDTO dltMapRoomPO:disProductMappingDTOList) {
                if(null!=mappingMap.get(dltMapRoomPO.getCompanyCode())) {
                    mappingMap.get(dltMapRoomPO.getCompanyCode()).add(dltMapRoomPO);
                }
            }
        }else {
            LOG.error("渠道配置表信息加载出错，或没有配置渠道对接信息！");
        }

        if(mappingMap.size() > 0) {
            for(String merchantCode : mappingMap.keySet()) {
                batchPushRoomDataService.pushRoomDataToDltByMapRoomList(mappingMap.get(merchantCode), DateUtil.getCurrentDate(),
                        DateUtil.getDate(DateUtil.getCurrentDate(), 60, 0),merchantCode);
            }
        }
    }
}
