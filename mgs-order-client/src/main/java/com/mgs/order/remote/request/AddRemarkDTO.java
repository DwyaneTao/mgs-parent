package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddRemarkDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 备注类型：0分销商备注，1供应商备注，2内部备注
     */
    private Integer remarkType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 接收对象
     */
    private String receiver;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
