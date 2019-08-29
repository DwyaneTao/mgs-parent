package com.mgs.ebk.order.remote.response;


import lombok.Data;

@Data
public class SupplyOrderRemark {



    /**
     * 客户
     */
    private String agentRemark;


    /**
     * 我方
     */
    private String supplierRemark;

}
