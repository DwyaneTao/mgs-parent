package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifySupplierOrderCodeDTO implements Serializable{

    /**
     * 供货单id
     */
    private Integer supplyOrderId;

    /**
     * 供应商订单号
     */
    private String supplierOrderCode;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
