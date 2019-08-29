package com.mgs.finance.remote.statement.request;

import com.mgs.common.BaseDTO;
import com.mgs.finance.remote.workorder.request.WorkOrderAttchDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class NotifyCollectionOfStatementDTO extends BaseDTO implements Serializable{

    /**
     * 账单id
     */
    private Integer statementId;

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
     * 凭证照片List
     */
    private List<WorkOrderAttchDTO> photoList;

    /**
     * 操作人
     */
    private String operator;
}
