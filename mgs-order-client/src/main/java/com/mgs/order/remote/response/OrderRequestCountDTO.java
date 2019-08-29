package com.mgs.order.remote.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderRequestCountDTO implements Serializable{

    private Integer orderId;

    /**
     * 取消申请数
     */
    private Integer cancelCount;

    /**
     * 修改申请数
     */
    private Integer modifyCount;
}
