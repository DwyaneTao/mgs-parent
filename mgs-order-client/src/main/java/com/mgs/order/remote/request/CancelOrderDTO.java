package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CancelOrderDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 退改费
     */
    private BigDecimal refundFee;

    /**
     * 取消原因
     */
    private String cancelledReason;

    /**
     * 取消内容
     */
    private String cancelledContent;

    /**
     * 申请id
     */
    private Integer orderRequestId;

    /**
     * 操作人
     */
    private String operator;


    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
