package com.mgs.finance.remote.workorder.response;

import com.mgs.common.BaseDTO;
import com.mgs.finance.remote.workorder.request.WorkOrderAttchDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderDTO extends BaseDTO implements Serializable{

    /**
     * 工单编码
     */
    private Integer workOrderId;

    /**
     * 工单状态：0未结算，1已结算，2已删除
     */
    private Integer workOrderStatus;

    /**
     * 支付方式
     */
    private Integer paymentType;
    /**
     * 支付币种
     */
    private  Integer currency;

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
     * 结算人
     */
    private String settledBy;

    /**
     * 结算时间
     */
    private Date settledDt;
}
