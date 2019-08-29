package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SaveSupplyAttachmentDTO implements Serializable{

    /**
     * 供货单id
     */
    private Integer supplyOrderId;

    /**
     * 文件名称
     */
    private String name;

    /**
     * url地址
     */
    private String url;

    /**
     * 实际地址
     */
    private String realpath;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
