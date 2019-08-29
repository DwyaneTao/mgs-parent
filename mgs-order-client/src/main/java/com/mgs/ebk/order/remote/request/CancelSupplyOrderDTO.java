package com.mgs.ebk.order.remote.request;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class CancelSupplyOrderDTO {



    /**
     * 单号
     */
    private Integer supplyOrderId;


    /**
     *  0:确认取消 1:拒绝取消
     */
    private  Integer cancelStatus;


    /**
     * 确认号
     */
    private String confirmationCode;


    /**
     * 备注
     */
    private String remark;

    /**
     * 操作人
     */
    private String operator;


    /**
     * 原因
     */
    private String cancelledReason;


    /**
     * 退订费
     */
    private BigDecimal refundFee;

}
