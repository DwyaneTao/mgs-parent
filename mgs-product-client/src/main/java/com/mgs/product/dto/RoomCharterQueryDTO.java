package com.mgs.product.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author py
 * @date 2019/7/18 19:46
 **/
@Data
public class RoomCharterQueryDTO {
    /**
     * 包房Id
     */
    private  String roomCharterId;
    /**
      * 包房批次编码
      */
    private  String roomCharterCode;
    /**
     * 包房批次名
     */
    private  String roomCharterName;
    /*
     *供应商名称
     */
    private  String supplierName;
    /**
     * 酒店名称
     */
    private  String hotelName;
    /**
     * 包房间夜数
     */
    private  Integer nightQty;
    /**
     * 合同金额
     */
    private BigDecimal  contractAmt;
    /**
     * 已付包房款
     */
    private  BigDecimal paidAmt;
    /**
     * 有效开始时间
     */
    private String startDate;
    /**
     * 有效结束时间
     */
    private String endDate;
    /**
     * 剩余到期天数
     */
    private  Integer remainingDays;
}
