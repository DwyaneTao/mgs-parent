package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SaveSupplyResultDTO implements Serializable{

    /**
     * 供货单id
     */
    private Integer supplyOrderId;

    /**
     * 确认状态：0未确认，1确认，2已取消
     */
    private Integer confirmationStatus;

    /**
     * 供应商确认人
     */
    private String supplierConfirmer;

    /**
     * 确认号
     */
    private String confirmationCode;

    /**
     * 拒绝原因
     */
    private String refusedReason;

    /**
     * 备注
     */
    private String remark;

    /**
     * 退改费
     */
    private BigDecimal refundFee;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
