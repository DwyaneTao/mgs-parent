package com.mgs.finance.workorder.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "f_work_order_item")
public class WorkOrderItemPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "work_order_id")
    private Integer workOrderId;

    /**
     * 业务单号
     */
    @Column(name = "business_code")
    private String businessCode;

    /**
     * 金额
     */
    private BigDecimal amount;

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

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return work_order_id
     */
    public Integer getWorkOrderId() {
        return workOrderId;
    }

    /**
     * @param workOrderId
     */
    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    /**
     * 获取业务单号
     *
     * @return business_code - 业务单号
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * 设置业务单号
     *
     * @param businessCode 业务单号
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * 获取金额
     *
     * @return amount - 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置金额
     *
     * @param amount 金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取创建人
     *
     * @return created_by - 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 设置创建人
     *
     * @param createdBy 创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取创建时间
     *
     * @return created_dt - 创建时间
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * 设置创建时间
     *
     * @param createdDt 创建时间
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * 获取修改人
     *
     * @return modified_by - 修改人
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * 设置修改人
     *
     * @param modifiedBy 修改人
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * 获取修改时间
     *
     * @return modified_dt - 修改时间
     */
    public Date getModifiedDt() {
        return modifiedDt;
    }

    /**
     * 设置修改时间
     *
     * @param modifiedDt 修改时间
     */
    public void setModifiedDt(Date modifiedDt) {
        this.modifiedDt = modifiedDt;
    }
}