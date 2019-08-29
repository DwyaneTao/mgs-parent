package com.mgs.finance.remote.workorder.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ConfirmWorkOrderDTO implements Serializable{

    /**
     * 工单id
     */
    private Integer workOrderId;

    /**
     * 支付方式
     */
    private Integer paymentType;

    /**
     * 付款方
     */
    private String payer;

    /**
     * 收款方
     */
    private String receiver;

    /**
     * 收款金额
     */
    private BigDecimal amt;

    /**
     * 备注
     */
    private String remark;

    /**
     * 凭证照片list
     */
    private List<WorkOrderAttchDTO> photoList;

    /**
     * 操作人
     */
    private String operator;
}
