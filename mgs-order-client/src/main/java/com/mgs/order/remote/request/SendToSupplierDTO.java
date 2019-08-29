package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SendToSupplierDTO implements Serializable{

    /**
     * 供货单id
     */
    private Integer supplyOrderId;

    /**
     * 发供货单类型：0预定单，1重发预订单，2修改单，3取消单
     */
    private Integer supplyOrderType;

    /**
     * 1EBK,2EMAIL,3FAX,4PHONE,5WECHAT,6QQ,7直连
     */
    private Integer sendingType;

    /**
     * 发单备注
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
}
