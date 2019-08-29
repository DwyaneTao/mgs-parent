package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class NotifyPaymentOfOrderListDTO implements Serializable{

    /**
     * 订单idList
     */
    private List<Integer> orderIdList;

    /**
     * 支付方式
     */
    private Integer paymentType;

    /**
     * 收款方
     */
    private String receiver;

    /**
     * 付款金额
     */
    private BigDecimal amt;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 订单归属人
     */
    private String orderOwnerName;

    /**
     * 工单附件
     */
    private List<NotifyAttchDTO> photoList;
}
