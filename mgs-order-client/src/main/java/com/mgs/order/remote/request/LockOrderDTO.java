package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class LockOrderDTO implements Serializable{

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 加解锁类型，1：加锁，0：解锁
     */
    private Integer lockType;

    /**
     * 操作人全名
     */
    private String operator;

    /**
     * 操作人账号
     */
    private String operatorUser;


    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
