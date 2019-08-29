package com.mgs.finance.remote.workorder.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderListResponseDTO implements Serializable{

    /**
     * 财务工单id
     */
    private Integer workOrderId;

    /**
     * 财务工单编号
     */
    private String workOrderCode;

    /**
     * 工单状态：0未结算，1已结算，2已删除
     */
    private Integer workOrderStatus;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 内容
     */
    private String content;

    /**
     * 业务单号
     */
    private String businessCodes;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 收款金额
     */
    private BigDecimal collectionAmt;

    /**
     * 付款金额
     */
    private BigDecimal paymentAmt;

    /**
     * 应处理完日期
     */
    private Date dueDate;

    /**
     * 逾期天数
     */
    private Integer overdueDays;

    /**
     * 业务类型：0订单，1供货单，2分销商账单，3供应商账单
     */
    private Integer businessType;

    /**
     * 币种
     */
    private Integer currency;

}
