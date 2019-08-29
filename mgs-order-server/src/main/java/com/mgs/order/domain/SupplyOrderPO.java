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
@Table(name = "o_supply_order")
public class SupplyOrderPO  extends BasePO {
    /**
     * 供货单信息
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 供货单编码
     */
    @Column(name = "supply_order_code")
    private String supplyOrderCode;

    /**
     * 确认状态：0未确认，1确认，2已取消
     */
    @Column(name = "confirmation_status")
    private Integer confirmationStatus;

    /**
     * 发单状态：0未发单，1已发预订单，2已重发预订单，3已发修改单，4已发取消单
     */
    @Column(name = "sending_status")
    private Integer sendingStatus;

    /**
     * 底价币种
     */
    @Column(name = "base_currency")
    private Integer baseCurrency;

    /**
     * 供货单价格
     */
    @Column(name = "supply_order_amt")
    private BigDecimal supplyOrderAmt;

    /**
     * 底价
     */
    @Column(name = "base_price")
    private BigDecimal basePrice;

    /**
     * 供应商编码
     */
    @Column(name = "supplier_code")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @Column(name = "supplier_name")
    private String supplierName;

    /**
     * 供应商订单号
     */
    @Column(name = "supplier_order_code")
    private String supplierOrderCode;

    /**
     * 结算方式：0月结 1半月结 2周结 3单结 4日结
     */
    @Column(name = "settlement_type")
    private Integer settlementType;

    /**
     * 退改费
     */
    @Column(name = "refund_fee")
    private BigDecimal refundFee;

    /**
     * 产品经理
     */
    @Column(name = "merchant_pm")
    private String merchantPm;

    /**
     * 产品币种转商家本币汇率
     */
    private BigDecimal rate;

    /**
     * 产品供应方式， 0商家自签，1系统直连
     */
    @Column(name = "purchase_type")
    private Integer purchaseType;

    /**
     * 城市编码
     */
    @Column(name = "city_code")
    private String cityCode;

    /**
     * 城市名称
     */
    @Column(name = "city_name")
    private String cityName;

    /**
     * 酒店id
     */
    @Column(name = "hotel_id")
    private Integer hotelId;

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
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 第一个产品的入住日期
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 第一个产品的离店日期
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 间数，第一个产品间数
     */
    @Column(name = "room_qty")
    private Integer roomQty;

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
     * 供应商确认号码
     */
    @Column(name = "confirmation_code")
    private String confirmationCode;

    /**
     * 确认人名称
     */
    @Column(name = "supplier_confirmer")
    private String supplierConfirmer;

    /**
     * 拒绝原因
     */
    @Column(name = "refused_reason")
    private String refusedReason;

    /**
     * 确认备注
     */
    @Column(name = "confirmation_remark")
    private String confirmationRemark;

}