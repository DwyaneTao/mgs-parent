package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSimpleDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 订单编码
     */
    private String orderCode;

    /**
     * 下单时间
     */
    private String createdDt;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 分销商名称
     */
    private String agentName;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 入住日期
     */
    private String startDate;

    /**
     * 离店日期
     */
    private String endDate;

    /**
     * 入住人
     */
    private String guest;

    /**
     * 间数
     */
    private Integer roomQty;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmt;

    /**
     * 订单结算状态：0未结算，1已结算
     */
    private Integer orderSettlementStatus;


    /**
     * 订单结算方式：0月结 1半月结 2周结 3单结 4日结
     */
    private Integer orderSettlementType;

    /**
     * 订单确认状态：0待确认，1已确认，2已取消
     */
    private Integer orderConfirmationStatus;

    /**
     * 供货单确认状态：0待确认，1已确认，2已取消
     */
    private Integer supplyOrderConfirmationStatus;

    /**
     * 归属人
     */
    private String orderOwnerName;

    /**
     * 订单标签
     */
    private List<String> orderTagList;

    /**
     * 确认号
     */
    private String confirmationCode;

    /**
     * 锁单人名称
     */
    private String lockName;

    /**
     * 标星：0未标，1已标
     */
    private Integer markedStatus;

    /**
     * 客人特殊要求
     */
    private Integer isShownOnSupplyOrder;

    /**
     * 供货单金额
     */
    private BigDecimal supplyOrderAmt;
}
