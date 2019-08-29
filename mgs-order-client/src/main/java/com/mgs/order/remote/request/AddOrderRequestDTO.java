package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddOrderRequestDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 订单申请类型，0取消单申请，1修改单申请
     */
    private Integer requestType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作人
     */
    private String operator;
}
