package com.mgs.ebk.order.remote.response;


import lombok.Data;

@Data
public class SupplyOrderStatistics {


    /**
     * 未处理订单数
     */
    private  Integer pendingOrderQty;


    /**
     * 今日入住订单数
     */
    private Integer checkInTodayOrderQty;


    /**
     * 取消申请数
     */
    private Integer cancelledRequestQty;


    /**
     * 修改申请数
     */
    private Integer modifyRequestQty;


}
