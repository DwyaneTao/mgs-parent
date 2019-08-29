package com.mgs.product.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author py
 * @date 2019/7/18 16:27
 **/
@Data
public class RoomCharterDTO extends BaseDTO {
    /**
     * 包房Id
     */
    private  Integer roomCharterId;
    /**
     * 供应商编码
     */
    private  String supplierCode;
    /**
     * 酒店Id
     */
    private  Integer hotelId;
    /**
     * 包房批次名
     */
    private  String roomCharterName;
    /**
     * 包房间夜数
     */
    private  Integer nightQty;
    /**
     * 有效开始时间
     */
    private Date startDate;
    /**
     * 有效结束时间
     */
    private  Date endDate;
    /**
     * 合同金额
     */
    private BigDecimal contractAmt;
    /**
     * 备注
     */
    private  String remark;
    /**
     * 运营商编码
     */
    private  String companyCode;
}
