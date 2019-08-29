package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryConfirmOrderInfoDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 确认类型：0确认，1取消
     */
    private Integer confirmType;
}

