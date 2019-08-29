package com.mgs.product.dto;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.mgs.common.BaseRequest;
import jdk.nashorn.internal.ir.RuntimeNode;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author py
 * @date 2019/7/20 11:44
 **/
@Data
public class QueryRoomCharterDetailDTO {
    /**
     * 包房批次编码
     */
    private  String roomCharterCode;
    /**
     * 包房名称
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
     * 已售间夜数
     */
    private  Integer soldNightQty;
    /**
     * 剩余间夜数
     */
    private  Integer remainingNightQty;
    /**
     * 合同金额
     */
    private BigDecimal contractAmt;
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
    /**
     * 已扣除金额
     */
    private  BigDecimal spentAmt;
    /**
     * 剩余金额
     */
    private  BigDecimal balance;
    /**
     * 备注
     */
    private  String remark;
}
