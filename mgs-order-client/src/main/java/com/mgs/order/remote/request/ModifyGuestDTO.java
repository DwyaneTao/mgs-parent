package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ModifyGuestDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 入住人
     */
    private List<String> guestList;

    /**
     * 操作人
     */
    private String operator;


    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
