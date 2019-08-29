package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatisticsDTO implements Serializable{

    /**
     * 未处理订单数
     */
    private Integer pendingOrderQty;

    /**
     * 未处理供货单数
     */
    private Integer pendingSupplyOrderQty;

    /**
     * 今日新单
     */
    private Integer todayNewOrderQty;

    /**
     * 今日入住订单数
     */
    private Integer checkInTodayOrderQty;

    /**
     * 取消申请数
     */
    private Integer cancelledRequestQty;

    /**
     * 明日入住订单数
     */
    private Integer checkInTomorrowOrderQty;

    /**
     * 我的订单数
     */
    private Integer myOrderQty;

    /**
     * 标星订单数
     */
    private Integer markedOrderQty;

    /**
     * 渠道订单列表
     */
    private List<ChannelOrderQtyDTO> channelList;
}
