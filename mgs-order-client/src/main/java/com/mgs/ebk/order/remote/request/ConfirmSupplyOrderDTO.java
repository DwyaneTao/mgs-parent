package com.mgs.ebk.order.remote.request;


import com.mgs.common.BaseRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class ConfirmSupplyOrderDTO implements Serializable {

    /**
     * 确认状态 0：确认订单 1：拒绝订单
     */
    private Integer confirmStatus;


    /**
     * 单号
     */
    private Integer supplyOrderId;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 确认号
     */
    private String confirmationCode;


    /**
     * 备注
     */
    private String remark;

}
