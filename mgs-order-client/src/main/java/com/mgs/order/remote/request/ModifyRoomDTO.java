package com.mgs.order.remote.request;

import lombok.Data;

import java.util.List;

@Data
public class ModifyRoomDTO {

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 房型id
     */
    private Integer roomId;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 登录名
     */
    private String loginName;
}

