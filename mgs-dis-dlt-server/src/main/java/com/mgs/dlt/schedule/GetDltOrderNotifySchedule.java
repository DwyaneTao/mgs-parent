package com.mgs.dlt.schedule;

import com.mgs.common.constant.InitData;
import com.mgs.dlt.domain.DltOrderPO;
import com.mgs.dlt.mapper.DltOrderMapper;
import com.mgs.dlt.request.base.PagingType;
import com.mgs.dlt.request.dto.GetDltOrderNotifyRequest;
import com.mgs.dlt.response.dto.GetDltOrderNotifyResponse;
import com.mgs.dlt.utils.DltInterfaceInvoker;
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
import java.util.*;


/**
 * 定时查询代理通订单变化通知
 *   2018/4/11.
 */
@Component
@Lazy(false)
@Configuration
@EnableScheduling
public class GetDltOrderNotifySchedule {

    private static final Logger LOG = LoggerFactory.getLogger(GetDltOrderNotifySchedule.class);

    private static final String CRON = "0/30 * * * * ?";
    //    private static final String CRON = "0 0/5 * * * ?";
    private volatile Date lastEndTime = null;

    @Autowired
    private DltOrderMapper dltOrderPOMapper;

    @Scheduled(cron = CRON)
    public void getDltOrderNotify() {
        try {
            GetDltOrderNotifyRequest request = new GetDltOrderNotifyRequest();
            Date currDate = new Date();
            Date startTime = (null == lastEndTime) ? DateUtil.getDate(currDate, 0, 0, -30) : lastEndTime;

            request.setStartTime(this.dateFormat(startTime));
            request.setEndTime(this.dateFormat(currDate));

            PagingType pagingType = new PagingType();
            pagingType.setPageSize(100);
            pagingType.setPageIndex(1);
            request.setPagingType(pagingType);

            //将映射关系按商家分组，分开拉取
            if(null!= InitData.channelConfigMap.keySet()) {
                for(String merchantCode : InitData.channelConfigMap.keySet()) {
                    List<String> orderIdList = this.queryOrderIdsFromDlt(request,merchantCode);
                    if (CollectionUtils.isNotEmpty(orderIdList)) {
                        List<DltOrderPO> dltOrderPOList = new ArrayList<>();
                        for (String orderId : orderIdList) {
                            DltOrderPO po = new DltOrderPO();
                            po.setDltOrderId(orderId);
                            po.setCompanyCode(merchantCode);
                            po.setCreatedDt(DateUtil.dateToString(new Date(),DateUtil.hour_format));
                            po.setCreatedBy("system");
                            po.setIsHandled(1);//默认是1
                            dltOrderPOList.add(po);
                        }
                        dltOrderPOMapper.insertList(dltOrderPOList);
                    }
                }
            }else {
                LOG.error("渠道配置表信息加载出错，或没有配置渠道对接信息！");
            }

            lastEndTime = currDate;
        } catch (Exception e) {
            LOG.error("同步订单提醒失败", e);
        }
    }

    private List<String> queryOrderIdsFromDlt(GetDltOrderNotifyRequest request,String merchantCode) {

        List<String> orderIdList = new ArrayList<>();
        GetDltOrderNotifyResponse response = DltInterfaceInvoker.invoke(request,merchantCode);
        if (null != response && null != response.getResultStatus() && 0 == response.getResultStatus().getResultCode()) {
            orderIdList.addAll(response.getDltOrderIds());

            // 递归分页获取数据
            PagingType pt = response.getPagingType();
            if (null != pt && pt.getTotalPages() > pt.getPageIndex()) {
                pt.setPageIndex(pt.getPageIndex() + 1);
                pt.setTotalPages(null);
                pt.setTotalRecords(null);
                request.setPagingType(pt);
                orderIdList.addAll(this.queryOrderIdsFromDlt(request,merchantCode));
            }
        }

        return orderIdList;
    }

    private String dateFormat(Date date) {
        return "/Date(" + date.getTime() + "+0800)/";
    }
}
