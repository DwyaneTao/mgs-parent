package com.mgs.order.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "o_supply_product")
public class SupplyProductPO  extends BasePO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 供货单ID
     */
    @Column(name = "supply_order_id")
    private Integer supplyOrderId;

    /**
     * 订单ID
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 房型id
     */
    @Column(name = "room_id")
    private Integer roomId;

    /**
     * 房型名称
     */
    @Column(name = "room_name")
    private String roomName;

    /**
     * 价格计划id
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 价格计划名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 采购类型：0自签协议房，1自签包房
     */
    @Column(name = "purchase_type")
    private Integer purchaseType;

    /**
     * 床型，0:单床，1：大床，2：双床，3：三床，4：四床，多个床型用逗号隔开
     */
    @Column(name = "bed_type")
    private String bedType;

    /**
     * 早餐数量
     */
    @Column(name = "breakfast_qty")
    private Integer breakfastQty;

    /**
     * 入住日期
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 离店日期
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 间数
     */
    @Column(name = "room_qty")
    private Integer roomQty;

    /**
     * 所有入住人名单，多个以逗号隔开
     */
    private String guest;

    /**
     * 总售价
     */
    @Column(name = "sale_price_total_amt")
    private BigDecimal salePriceTotalAmt;

    /**
     * 总底价
     */
    @Column(name = "base_price_total_amt")
    private BigDecimal basePriceTotalAmt;

    @Column(name = "quota_account_id")
    private Integer quotaAccountId;
}