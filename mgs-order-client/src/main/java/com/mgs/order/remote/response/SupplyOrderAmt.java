package com.mgs.order.remote.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SupplyOrderAmt {

    /**
     * 订单Id
     */
    private Integer orderId;

    /**
     * 订单供货单总金额
     */
    private BigDecimal amt;


    private  Integer baseCurrency;


}
