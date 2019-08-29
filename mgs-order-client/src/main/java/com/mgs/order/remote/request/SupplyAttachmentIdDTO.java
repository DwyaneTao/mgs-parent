package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SupplyAttachmentIdDTO implements Serializable{

    /**
     * 供货单附件id
     */
    private Integer supplyAttachmentId;

    /**
     * 操作人
     */
    private String operator;


    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
