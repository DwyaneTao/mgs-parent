package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConfirmOrderDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 确认类型：0在线
     */
    private String confirmationType;

    /**
     * 确认内容
     */
    private String confirmationContent;

    /**
     * 确认号
     */
    private String confirmationCode;

    /**
     * 操作人
     */
    private String operator;


    /**
     * 商家编码
     */
    private String companyCode;

    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
