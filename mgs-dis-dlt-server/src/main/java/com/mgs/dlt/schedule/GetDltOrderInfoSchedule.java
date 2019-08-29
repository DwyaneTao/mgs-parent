package com.mgs.dlt.schedule;


import com.mgs.dlt.domain.DltOrderPO;
import com.mgs.dlt.mapper.DltOrderMapper;
import com.mgs.dlt.request.dto.GetDltOrderInfoRequest;
import com.mgs.dlt.response.dto.DltOrderInfo;
import com.mgs.dlt.response.dto.GetDltOrderInfoResponse;
import com.mgs.dlt.service.DltHotelOrderService;
import com.mgs.dlt.utils.DltInterfaceInvoker;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


/**
 * 定时查询代理通订单变化通知
 *   2018/4/11.
 */
@Component
@Lazy(false)
@Configuration
@EnableScheduling
public class GetDltOrderInfoSchedule {

    private static final Logger LOG = LoggerFactory.getLogger(GetDltOrderInfoSchedule.class);

    //0/30 * * * * ?
    private static final String CRON = "0/30 * * * * ?";

    private static volatile Set<String> inProcessingOrderSet = new HashSet<>();
    @Autowired
    private DltOrderMapper dltOrderPOMapper;

    @Autowired
    private DltHotelOrderService dltHotelOrderService;

    @Scheduled(cron = CRON)
    public void getDltOrderInfo() {

        // 从t_dlt_order表中取出所有未处理的订单号
        Example example = new Example(DltOrderPO.class);
        // 0 是处理成功，其他都是处理的次数，每处理一次累加1，超过3次不处理了
        example.createCriteria().andBetween("isHandled",1, 3);
        example.setOrderByClause("created_dt");
        List<DltOrderPO> dltOrderPOList = dltOrderPOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(dltOrderPOList)) {
            LOG.error("没有需要同步的订单信息");
            return;
        }

        // 合并订单号
        Map<String, DltOrderPO> orderMap = new HashMap<>();
        dltOrderPOList.forEach(po -> {
            if (!orderMap.keySet().contains(po.getDltOrderId())) {
                orderMap.put(po.getDltOrderId(), po);
            } else {
                // 选一个处理次数小的作为实际处理次数，尽可能多处理计次直至成功
                DltOrderPO temp = orderMap.get(po.getDltOrderId());
                temp.setIsHandled(temp.getIsHandled() > po.getIsHandled() ? po.getIsHandled() : temp.getIsHandled());
            }
        });

        // 循环查询dlt接口，获取订单详情
        for (Map.Entry<String, DltOrderPO> entry : orderMap.entrySet()) {
            if (!inProcessingOrderSet.contains(entry.getKey())) {
                synchronized (this) {
                    if (!inProcessingOrderSet.contains(entry.getKey())) {
                        LOG.info("开始同步订单，DltOrderId=" + entry.getKey());
                        inProcessingOrderSet.add(entry.getKey());
                        LOG.info("开始同步订单，订单号被加入到去重集合,DltOrderId=" + entry.getKey());
                    } else {
                        continue;
                    }
                }
            } else {
                // 如果正在处理，则跳过，避免订单被多个定时任务重复处理
                continue;
            }
            this.getDltOrderInfo(entry.getKey(),entry.getValue().getCompanyCode());
            inProcessingOrderSet.remove(entry.getKey());
            LOG.info("同步订单结束：订单号从去重集合中删除,DltOrderId=" + entry.getKey());
        }

    }

    public void getDltOrderInfo(String dltOrderId,String merchantCode) {
        GetDltOrderInfoRequest request = new GetDltOrderInfoRequest();
        request.setDltOrderId(dltOrderId);
        GetDltOrderInfoResponse response = DltInterfaceInvoker.invoke(request,merchantCode);
        if (null == response || null == response.getResultStatus() || 0 != response.getResultStatus().getResultCode()
                || null == response.getDltOrderInfo()) {
            dltHotelOrderService.updateOrderHandleResult(dltOrderId, false, "调用代理通接口返回空");
            return;
        }

        try {
            DltOrderInfo orderInfo = response.getDltOrderInfo();
            dltHotelOrderService.createOrder(orderInfo,merchantCode);
        } catch (Exception e) {
            LOG.error("同步代理通订单到订单系统出错", e);
        }
    }
}
