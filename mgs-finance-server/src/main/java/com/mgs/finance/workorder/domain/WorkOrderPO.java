package com.mgs.finance.workorder.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "f_work_order")
public class WorkOrderPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 工单号
     */
    @Column(name = "work_order_code")
    private String workOrderCode;

    /**
     * 工单状态：0未结算，1已结算，2已删除
     */
    @Column(name = "work_order_status")
    private Integer workOrderStatus;

    /**
     * 业务类型：0订单，1供货单，2分销商账单，3供应商账单
     */
    @Column(name = "business_type")
    private Integer businessType;

    /**
     * 业务单号
     */
    @Column(name = "business_code")
    private String businessCode;

    /**
     * 机构编码
     */
    @Column(name = "org_code")
    private String orgCode;

    /**
     * 机构名称
     */
    @Column(name = "org_name")
    private String orgName;

    /**
     * 工单内容
     */
    private String content;

    /**
     * 币种
     */
    private Integer currency;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 付款方式：0线下转账
     */
    @Column(name = "payment_type")
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
     * 备注
     */
    private String remark;

    /**
     * 商家编码
     */
    @Column(name = "company_code")
    private String companyCode;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @Column(name = "created_dt")
    private Date createdDt;

    /**
     * 修改人
     */
    @Column(name = "modified_by")
    private String modifiedBy;

    /**
     * 修改时间
     */
    @Column(name = "modified_dt")
    private Date modifiedDt;
}