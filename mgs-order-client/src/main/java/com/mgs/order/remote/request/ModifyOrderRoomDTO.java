package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifyOrderRoomDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 房型id
     */
    private Integer roomId;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 操作人
     */
    private String operator;


    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
