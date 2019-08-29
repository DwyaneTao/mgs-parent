package com.mgs.finance.workorder.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "f_work_order_attch")
public class WorkOrderAttchPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 工单id
     */
    @Column(name = "work_order_id")
    private Integer workOrderId;

    /**
     * 附件名称
     */
    private String name;

    /**
     * url地址
     */
    private String url;

    /**
     * 实际地址
     */
    private String realpath;

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
     * 获取工单id
     *
     * @return work_order_id - 工单id
     */
    public Integer getWorkOrderId() {
        return workOrderId;
    }

    /**
     * 设置工单id
     *
     * @param workOrderId 工单id
     */
    public void setWorkOrderId(Integer workOrderId) {
        this.workOrderId = workOrderId;
    }

    /**
     * 获取附件名称
     *
     * @return name - 附件名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置附件名称
     *
     * @param name 附件名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取url地址
     *
     * @return url - url地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置url地址
     *
     * @param url url地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取实际地址
     *
     * @return realpath - 实际地址
     */
    public String getRealpath() {
        return realpath;
    }

    /**
     * 设置实际地址
     *
     * @param realpath 实际地址
     */
    public void setRealpath(String realpath) {
        this.realpath = realpath;
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