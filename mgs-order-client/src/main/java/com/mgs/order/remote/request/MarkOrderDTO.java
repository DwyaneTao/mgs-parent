package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class MarkOrderDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 标星：0未标，1已标
     */
    private Integer markedStatus;
    /**
     * 操作人
     */
    private  String operator;


    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
