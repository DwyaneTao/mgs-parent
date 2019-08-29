package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class HandleOrderRequestDTO implements Serializable{

    /**
     * 订单请求id
     */
    private Integer orderRequestId;

    /**
     * 处理结果：1同意处理，2拒绝处理
     */
    private Integer handledResult;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作人
     */
    private String operator;


    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
