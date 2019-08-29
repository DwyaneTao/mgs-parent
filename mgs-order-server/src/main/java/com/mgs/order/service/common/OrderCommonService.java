package com.mgs.order.service.common;

import com.mgs.enums.ChannelEnum;
import com.mgs.order.domain.OrderLogPO;
import com.mgs.order.domain.OrderPO;
import com.mgs.order.mapper.OrderLogMapper;
import com.mgs.order.mapper.OrderMapper;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.mgs.util.DateUtil.hour_format;

@Service
public class OrderCommonService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    /**
     * 保存订单日志
     * @param orderId
     * @param operator
     * @param target
     * @param content
     */
    @Async
    public void saveOrderLog(Integer orderId,
                             String operator,
                             String orderOwnerName,
                             String target,
                             String content){

        OrderLogPO orderLogPO1=new OrderLogPO();
        orderLogPO1.setOrderId(orderId);
        Integer  orderLogCount= orderLogMapper.selectCount(orderLogPO1);
        if((null==orderLogCount || orderLogCount==0) && !operator.equals(ChannelEnum.CTRIP.key)
                && !operator.equals(ChannelEnum.QUNAR.key)
                && !operator.equals(ChannelEnum.FEIZHU.key)
                && !operator.equals(ChannelEnum.MEITUAN.key)
                && !operator.equals(ChannelEnum.CTRIP_B2B.key)
                && !operator.equals(ChannelEnum.CTRIP_CHANNEL_A.key)
                && !operator.equals(ChannelEnum.TCYL.key)){
            //更新订单归属人
            OrderPO orderPO=orderMapper.selectByPrimaryKey(orderId);
            if (!StringUtil.isValidString(orderPO.getOrderOwnerName())
                    && StringUtil.isValidString(operator)){
                OrderPO orderUpdate=new OrderPO();
                orderUpdate.setId(orderId);
                orderUpdate.setOrderOwnerName(orderOwnerName);
                orderUpdate.setOrderOwnerUser(orderOwnerName);
                orderMapper.updateByPrimaryKeySelective(orderUpdate);
            }
        }

        //记日志
        OrderLogPO orderLogPO=new OrderLogPO();
        orderLogPO.setOrderId(orderId);
        orderLogPO.setCreatedBy(operator);
        orderLogPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
        orderLogPO.setTarget(target);
        orderLogPO.setContent(content);
        orderLogMapper.insert(orderLogPO);


    }
}
