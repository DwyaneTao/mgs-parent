package com.mgs.order.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "o_order_product_price")
public class OrderProductPricePO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 日期
     */
    @Column(name = "sale_date")
    private Date saleDate;

    /**
     * 价格
     */
    @Column(name = "sale_price")
    private BigDecimal salePrice;

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
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取日期
     *
     * @return sale_date - 日期
     */
    public Date getSaleDate() {
        return saleDate;
    }

    /**
     * 设置日期
     *
     * @param saleDate 日期
     */
    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    /**
     * 获取价格
     *
     * @return sale_price - 价格
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * 设置价格
     *
     * @param salePrice 价格
     */
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }
}