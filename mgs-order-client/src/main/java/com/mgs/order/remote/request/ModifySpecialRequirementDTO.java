package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifySpecialRequirementDTO implements Serializable{

    /**
     * 订单Id
     */
    private Integer orderId;

    /**
     * 特殊要求
     */
    private String specialRequest;

    /**
     * 是否显示在供货单:0不展示，1展示
     */
    private Integer isShownOnSupplyOrder;

    /**
     * 操作人
     */
    private String operator;


    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
