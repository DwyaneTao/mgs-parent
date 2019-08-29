package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class NotifyCollectionOfSupplyOrderListDTO implements Serializable{

    /**
     * 供货单id
     */
    private List<Integer> supplyOrderIdList;

    /**
     * 支付方式
     */
    private Integer paymentType;

    /**
     * 付款方
     */
    private String payer;

    /**
     * 收款方
     */
    private String receiver;

    /**
     * 收款金额
     */
    private BigDecimal amt;

    /**
     * 备注
     */
    private String remark;

    /**
     * 凭证照片List
     */
    private List<NotifyAttchDTO> photoList;

    /**
     * 操作人
     */
    private String operator;


    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
