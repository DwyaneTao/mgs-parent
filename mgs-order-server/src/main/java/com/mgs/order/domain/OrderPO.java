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
@Table(name = "o_order")
public class OrderPO  extends BasePO {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单编码
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 订单确认状态：0待确认，1已确认，2已取消
     */
    @Column(name = "order_confirmation_status")
    private Integer orderConfirmationStatus;

    /**
     * 售价币种
     */
    @Column(name = "sale_currency")
    private Integer saleCurrency;

    /**
     * 订单金额
     */
    @Column(name = "order_amt")
    private BigDecimal orderAmt;

    /**
     * 售价
     */
    @Column(name = "sale_price")
    private BigDecimal salePrice;

    /**
     * 结算方式：0月结 1半月结 2周结 3单结 4日结
     */
    @Column(name = "settlement_type")
    private Integer settlementType;

    /**
     * 渠道编码
     */
    @Column(name = "channel_code")
    private String channelCode;

    /**
     * 分销商编码
     */
    @Column(name = "agent_code")
    private String agentCode;

    /**
     * 分销商名称
     */
    @Column(name = "agent_name")
    private String agentName;

    /**
     * 联系人名称
     */
    @Column(name = "contact_name")
    private String contactName;

    /**
     * 联系人电话
     */
    @Column(name = "contact_phone")
    private String contactPhone;

    /**
     * 渠道订单号
     */
    @Column(name = "channel_order_code")
    private String channelOrderCode;

    /**
     * 客人特殊要求
     */
    @Column(name = "special_request")
    private String specialRequest;

    /**
     * 客人特殊要求
     */
    @Column(name = "is_show_on_supply_order")
    private Integer isShowOnSupplyOrder;

    /**
     * 订单归属人帐号
     */
    @Column(name = "order_owner_user")
    private String orderOwnerUser;

    /**
     * 订单归属人名称
     */
    @Column(name = "order_owner_name")
    private String orderOwnerName;

    /**
     * 锁单人帐号
     */
    @Column(name = "lock_user")
    private String lockUser;

    /**
     * 锁单人名称
     */
    @Column(name = "lock_name")
    private String lockName;

    /**
     * 锁单时间
     */
    @Column(name = "lock_time")
    private Date lockTime;

    /**
     * 总利润
     */
    private BigDecimal profit;

    /**
     * 是否手工单
     */
    @Column(name = "is_manual_order")
    private Integer isManualOrder;

    /**
     * 是否代下单
     */
    @Column(name = "is_substituted")
    private Integer isSubstituted;

    /**
     * 商家编码
     */
    @Column(name = "company_code")
    private String companyCode;


    /**
     * 业务经理
     */
    @Column(name = "merchant_bm")
    private String merchantBm;

    /**
     * 退改费
     */
    @Column(name = "refund_fee")
    private BigDecimal refundFee;

    /**
     * 取消原因
     */
    @Column(name = "cancelled_reason")
    private String cancelledReason;

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
     * 房型id
     */
    private Integer roomId;

    /**
     * 房型名称，多个以逗号隔开
     */
    @Column(name = "room_name")
    private String roomName;

    /**
     * 产品名称，多个以逗号隔开
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
     * 间数
     */
    @Column(name = "room_qty")
    private Integer roomQty;

    /**
     * 所有入住人名单，多个以逗号隔开
     */
    private String guest;

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
     * 确认号
     */
    @Column(name = "confirmation_code")
    private String confirmationCode;

    /**
     * 供货单状态，多个供货单状态已逗号隔开，0未发单，1已发预订单，2已重发预订单，3已发修改单，4已发取消单
     */
    @Column(name = "supply_order_confirmation_status")
    private String supplyOrderConfirmationStatus;

    /**
     * 确认时间
     */
    @Column(name = "confirm_time")
    private Date confirmTime;

    /**
     * 标星：0未标，1已标
     */
    @Column(name = "marked_status")
    private Integer markedStatus;

    /**
     * 修改状态：0无，1取消中，2修改中
     */
    @Column(name = "modification_status")
    private Integer modificationStatus;

    /**
     * 是否即时确认： 0是，1否
     */
    @Column(name = "instantConfimation_status")
    private Integer instantConfimationStatus;

}