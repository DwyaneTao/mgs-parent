package com.mgs.order.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "o_supply_product_price")
public class SupplyProductPricePO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 供货产品id
     */
    @Column(name = "supply_product_id")
    private Integer supplyProductId;

    /**
     * 供货单id
     */
    @Column(name = "supply_order_id")
    private Integer supplyOrderId;

    /**
     * 日期
     */
    @Column(name = "sale_date")
    private Date saleDate;

    /**
     * 底价
     */
    @Column(name = "sale_price")
    private BigDecimal salePrice;

    /**
     * 底价
     */
    @Column(name = "base_price")
    private BigDecimal basePrice;

    /**
     * 扣配额数
     */
    @Column(name = "debucted_quota")
    private Integer debuctedQuota;
}