package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderAttachmentIdDTO implements Serializable{

    /**
     * 订单附件id
     */
    private Integer orderAttachmentId;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
