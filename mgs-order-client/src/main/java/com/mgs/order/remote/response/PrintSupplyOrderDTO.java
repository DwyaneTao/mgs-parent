package com.mgs.order.remote.response;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PrintSupplyOrderDTO {

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 酒店地址
     */
    private String hotelAddress;

    /**
     * 酒店电话
     */
    private String hotelTel;

    /**
     * 入住人
     */
    private String guest;

    /**
     * 特殊要求
     */
    private String specialRequest;



    private  List<SupplyOrderListDTO>  supplyOrderList;


}
