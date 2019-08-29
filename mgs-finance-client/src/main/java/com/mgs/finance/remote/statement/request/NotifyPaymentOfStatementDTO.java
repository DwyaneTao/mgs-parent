package com.mgs.finance.remote.statement.request;

import com.mgs.finance.remote.workorder.request.WorkOrderAttchDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class NotifyPaymentOfStatementDTO implements Serializable{

    /**
     * 账单id
     */
    private Integer statementId;

    /**
     * 支付方式
     */
    private Integer paymentType;

    /**
     * 收款方
     */
    private String receiver;

    /**
     * 付款金额
     */
    private BigDecimal amt;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 工单附件
     */
    private List<WorkOrderAttchDTO> photoList;
}
