package com.mgs.finance.remote.statement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementOrderDTO implements Serializable{

    /**
     * 账单明细id
     */
    private Integer statementOrderId;

    /**
     * 订单编码
     */
    private String orderCode;

    /**
     * 创建时间
     */
    private Date createdDt;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 入住日期
     */
    private Date startDate;

    /**
     * 离店日期
     */
    private Date endDate;

    /**
     * 入住人
     */
    private String guest;

    /**
     * 间数
     */
    private Integer roomQty;

    /**
     * 应收金额
     */
    private BigDecimal receivableAmt;

    /**
     * 币种
     */
    private Integer currency;

    /**
     * 确认状态：0未确认，1确认，2已取消
     */
    private Integer confirmationStatus;
    /**
     * 确认信息
     */
    private  String confirmationStatusStr;
}
