package com.mgs.finance.statement.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "f_supplier_statement_order")
public class SupplierStatementOrderPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 账单id
     */
    @Column(name = "statement_id")
    private Integer statementId;

    /**
     * 供货单id
     */
    @Column(name = "supply_order_id")
    private Integer supplyOrderId;

    /**
     * 供货单号
     */
    @Column(name = "supply_order_code")
    private String supplyOrderCode;

    /**
     * 酒店名称
     */
    @Column(name = "hotel_name")
    private String hotelName;

    /**
     * 房型名称
     */
    @Column(name = "room_name")
    private String roomName;

    /**
     * 价格计划名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 入住人
     */
    private String guest;

    /**
     * 开始日期
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 结束日期
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 间数
     */
    @Column(name = "room_qty")
    private Integer roomQty;

    /**
     * 币种
     */
    private Integer currency;

    /**
     * 应收
     */
    @Column(name = "payable_amt")
    private BigDecimal payableAmt;

    /**
     * 订单创建时间
     */
    @Column(name = "order_create_date")
    private Date orderCreateDate;

    /**
     * 确认状态：0待确认，1已确认，2已取消
     */
    @Column(name = "confirmation_status")
    private Integer confirmationStatus;

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
     * 获取账单id
     *
     * @return statement_id - 账单id
     */
    public Integer getStatementId() {
        return statementId;
    }

    /**
     * 设置账单id
     *
     * @param statementId 账单id
     */
    public void setStatementId(Integer statementId) {
        this.statementId = statementId;
    }

    /**
     * 获取供货单id
     *
     * @return supply_order_id - 供货单id
     */
    public Integer getSupplyOrderId() {
        return supplyOrderId;
    }

    /**
     * 设置供货单id
     *
     * @param supplyOrderId 供货单id
     */
    public void setSupplyOrderId(Integer supplyOrderId) {
        this.supplyOrderId = supplyOrderId;
    }

    /**
     * 获取供货单号
     *
     * @return supply_order_code - 供货单号
     */
    public String getSupplyOrderCode() {
        return supplyOrderCode;
    }

    /**
     * 设置供货单号
     *
     * @param supplyOrderCode 供货单号
     */
    public void setSupplyOrderCode(String supplyOrderCode) {
        this.supplyOrderCode = supplyOrderCode;
    }

    /**
     * 获取酒店名称
     *
     * @return hotel_name - 酒店名称
     */
    public String getHotelName() {
        return hotelName;
    }

    /**
     * 设置酒店名称
     *
     * @param hotelName 酒店名称
     */
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    /**
     * 获取房型名称
     *
     * @return room_name - 房型名称
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * 设置房型名称
     *
     * @param roomName 房型名称
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * 获取价格计划名称
     *
     * @return product_name - 价格计划名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置价格计划名称
     *
     * @param productName 价格计划名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取入住人
     *
     * @return guest - 入住人
     */
    public String getGuest() {
        return guest;
    }

    /**
     * 设置入住人
     *
     * @param guest 入住人
     */
    public void setGuest(String guest) {
        this.guest = guest;
    }

    /**
     * 获取开始日期
     *
     * @return start_date - 开始日期
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置开始日期
     *
     * @param startDate 开始日期
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取结束日期
     *
     * @return end_date - 结束日期
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置结束日期
     *
     * @param endDate 结束日期
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取间数
     *
     * @return room_qty - 间数
     */
    public Integer getRoomQty() {
        return roomQty;
    }

    /**
     * 设置间数
     *
     * @param roomQty 间数
     */
    public void setRoomQty(Integer roomQty) {
        this.roomQty = roomQty;
    }

    /**
     * 获取币种
     *
     * @return currency - 币种
     */
    public Integer getCurrency() {
        return currency;
    }

    /**
     * 设置币种
     *
     * @param currency 币种
     */
    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    /**
     * 获取应收
     *
     * @return payable_amt - 应收
     */
    public BigDecimal getPayableAmt() {
        return payableAmt;
    }

    /**
     * 设置应收
     *
     * @param payableAmt 应收
     */
    public void setPayableAmt(BigDecimal payableAmt) {
        this.payableAmt = payableAmt;
    }

    /**
     * 获取订单创建时间
     *
     * @return order_create_date - 订单创建时间
     */
    public Date getOrderCreateDate() {
        return orderCreateDate;
    }

    /**
     * 设置订单创建时间
     *
     * @param orderCreateDate 订单创建时间
     */
    public void setOrderCreateDate(Date orderCreateDate) {
        this.orderCreateDate = orderCreateDate;
    }

    /**
     * 获取确认状态：0待确认，1已确认，2已取消
     *
     * @return confirmation_status - 确认状态：0待确认，1已确认，2已取消
     */
    public Integer getConfirmationStatus() {
        return confirmationStatus;
    }

    /**
     * 设置确认状态：0待确认，1已确认，2已取消
     *
     * @param confirmationStatus 确认状态：0待确认，1已确认，2已取消
     */
    public void setConfirmationStatus(Integer confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
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