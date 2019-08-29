package com.mgs.statistics.dto;

import com.mgs.common.BaseRequest;
import lombok.Data;

@Data
public class QuerySaleStatisticsDTO extends BaseRequest {


    /**
     * 供货单确认状态：0待确认，1已确认，2已取消，3未发单
     */
    private  Integer supplyOrderConfirmationStatus;

    /**
     * 酒店确认号
     */
    private  String confirmationCode;

    /**
     * 开始时间
     */
    private  String startDate;

    /**
     * 开始时间
     */
    private  String endDate;


    /**
     * 供货单应付(0:不等于0，1：等于0)
     */
    private  Integer supplyOrderPayableType;

    /**
     * 客户编码
     */
    private  String agentCode;

    /**
     * 采购经理ID
     */
    private  Integer purchaseManagerId;


    /**
     * 归属人
     */
    private  String orderOwnerName;

    /**
     * 客户渠道
     */
    private  String channelCode;

    /**
     * 订单确认状态 0待确认，1已确认，2已取消，3新建，4取消申请中
     */
    private  Integer orderConfirmationStatus;

    /**
     * 酒店ID
     */
    private  Integer hotelId;

    /**
     * 订单编号
     */
    private  String orderCode;

    /**
     * 订单应收(0:不等于0，1：等于0)
     */
    private  Integer orderReceivableType;

    /**
     * 供应商编码
     */
    private  String supplierCode;

    /**
     * 销售经理ID
     */
    private  Integer saleManagerId;

    /**
     * 运营商编码
     */
    private  String companyCode;

    /**
     * 日期查询类型：0下单日期，1入住日期，2离店日期
     */
    private  Integer dateQueryType;



}
