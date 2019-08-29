package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifySupplyOrderSettlementTypeDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer supplyOrderId;

    /**
     * 结算方式：0月结 1半月结 2周结 3单结 4日结
     */
    private Integer settlementType;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
