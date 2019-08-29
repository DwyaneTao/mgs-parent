package com.mgs.finance.remote.workorder.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationLogDTO implements Serializable{

    /**
     * 工单编码
     */
    private String workOrderCode;

    /**
     * 工单状态：0未结算，1已结算，2已删除
     */
    private Integer workOrderStatus;

    /**
     * 收款金额
     */
    private BigDecimal collectionAmt;

    /**
     * 付款金额
     */
    private BigDecimal paymentAmt;

    /**
     * 支付方式
     */
    private Integer paymentType;

    /**
     * 创建时间
     */
    private Date createdDt;

    /**
     * 创建人
     */
    private String createdBy;
}
