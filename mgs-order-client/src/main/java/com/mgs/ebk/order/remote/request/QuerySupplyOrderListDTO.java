package com.mgs.ebk.order.remote.request;


import com.mgs.common.BaseRequest;
import lombok.Data;

@Data
public class QuerySupplyOrderListDTO extends BaseRequest {

    /**
     * 供货单状态：0待确认，1已确认，2已取消 ,3修改申请，4取消申请
     */
    private Integer orderConfirmationStatus;

    /**
     * 单号
     */
    private String orderCode;


    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 入住人
     */
    private String guest;


    /**
     * 确认号
     */
    private String confirmationCode;

    /**
     * 供货单结算状态：0未结算，1已结算
     */
    private Integer orderSettlementStatus;


    /**
     * 日期查询类型：0下单日期，1入住日期，2离店日期
     */
    private  Integer dateQueryType;


}
