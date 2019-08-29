package com.mgs.finance.remote.lock.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class FinanceLockOrderDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 锁类型：0解锁，1加锁
     */
    private Integer lockStatus;

    /**
     * 操作人
     */
    private String operator;
}
