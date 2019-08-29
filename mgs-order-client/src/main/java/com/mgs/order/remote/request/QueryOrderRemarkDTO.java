package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryOrderRemarkDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 备注类型：0: 分销商备注，1：供应商备注 ，2：内部备注
     */
    private Integer remarkType;
}
