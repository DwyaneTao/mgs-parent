package com.mgs.order.remote.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SupplyOrderListDTO {

    private  String supplyOrderCode;


    private  Integer  currency;


    /**
     * 产品列表
     */
    private List<SupplyProductPreviewDTO> productList;


    private BigDecimal totalAmt;

}
