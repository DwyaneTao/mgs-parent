package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderCodeDTO implements Serializable{

    /**
     * 订单编码
     */
    private String orderCode;

    /**
     * 供货单编码
     */
    private String supplyOrderCode;

    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
