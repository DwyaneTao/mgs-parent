package com.mgs.product.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author py
 * @date 2019/7/18 15:48
 **/
@Data
@Table(name = "f_room_charter")
public class RoomCharterPO  extends BasePO {
    /**
     * 包房Id
     */
    @Id
    @Column(name = "room_charter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer roomCharterId;
    /**
     * 包房批次编码
     */
    private  String roomCharterCode;
    /**
     * 包房名称
     */
    private  String roomCharterName;
    /**
     * 酒店Id
     */
    private  Integer hotelId;
    /**
     * 供应商编码
     */
    private  String supplierCode;
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
     * 已付包房款
     */
    private  BigDecimal paidAmt;
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
