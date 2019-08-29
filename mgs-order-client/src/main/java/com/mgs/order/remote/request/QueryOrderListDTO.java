package com.mgs.order.remote.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

@Data
public class QueryOrderListDTO extends BaseRequest{

    /**
     * 订单编码
     */
    private String orderCode;

    /**
     * 入住人
     */
    private String guest;

    /**
     * 渠道订单号
     */
    private String channelOrderCode;

    /**
     * 订单确认状态：0待确认，1已确认，2已取消
     */
    private Integer orderConfirmationStatus;

    /**
     * 日期查询类型：0下单日期，1入住日期，2离店日期
     */
    private Integer dateQueryType;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 酒店id
     */
    private Integer hotelId;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 我方供应商负责人
     */
    private Integer purchaseManagerId;

    /**
     * 供货单状态：0待确认，1已确认，2已取消
     */
    private Integer supplyOrderConfirmationStatus;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 订单结算方式：1月结2半月结3周结4单结5日结
     */
    private Integer orderSettlementType;

    /**
     * 订单结算状态：0未结算，1已结算
     */
    private Integer orderSettlementStatus;

    /**
     * 供货单结算方式：1月结2半月结3周结4单结5日结
     */
    private Integer supplyOrderSettlementType;

    /**
     * 供货单结算状态：0未结算，1已结算
     */
    private Integer supplyOrderSettlementStatus;

    /**
     * 供货单号
     */
    private String supplyOrderCode;

    /**
     * 确认号
     */
    private String confirmationCode;

    /**
     * 供货方单号
     */
    private String supplierOrderCode;

    /**
     * 标星状态
     */
    private Integer markedStatus;

    /**
     * 商家编码
     */
    private String companyCode;

    /**
     * 是否我的订单
     */
    private Integer isMyOrder;

    /**
     * 操作人
     */
    private String operator;
}
