package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ModifySupplyProductDTO implements Serializable{

    /**
     * 供货单产品id
     */
    private Integer supplyProductId;

    /**
     * 入住日期
     */
    private String startDate;

    /**
     * 离店日期
     */
    private String endDate;

    /**
     * 间数
     */
    private Integer roomQty;

    /**
     * 入住人
     */
    private String guest;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 价格列表
     */
    private List<PriceRequestDTO> priceList;

    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
