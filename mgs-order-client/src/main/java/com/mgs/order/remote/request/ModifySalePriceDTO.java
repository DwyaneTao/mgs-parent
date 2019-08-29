package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ModifySalePriceDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 价格列表
     */
    private List<PriceRequestDTO> salePriceList;


    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
