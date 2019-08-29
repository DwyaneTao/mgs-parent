package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderIdDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;
}
