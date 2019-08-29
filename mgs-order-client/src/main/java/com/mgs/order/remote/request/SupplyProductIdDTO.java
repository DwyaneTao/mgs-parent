package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SupplyProductIdDTO implements Serializable{

    /**
     * 供货单产品id
     */
    private Integer supplyProductId;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
