package com.mgs.ebk.order.remote.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SupplyOrderListDTO implements Serializable {


    private Integer orderId;


    /**
     * 单号
     */
    private String orderCode;


    /**
     * 下单时间
     */
    private String createdDt;


    /**
     * 房型名称
     */
    private String roomName;


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
     * 间数
     */
    private Integer roomQty;

    /**
     * 供货单金额
     */
    private BigDecimal orderAmt;


    /**
     * 供货单状态：0待确认，1已确认，2已取消 ,3申请修改，4申请取消
     */
    private Integer orderConfirmationStatus;


    /**
     * EBK供货单状态：0待确认，1已确认，2已取消
     */
    private Integer ebkOrderConfirmationStatus;


    /**
     * 供货单结算状态：0未结算，1已结算
     */
    private Integer orderSettlementStatus;



    /**
     * 供货单发单状态：0未结算，1已结算
     */
    private Integer sendingStatus;




}
