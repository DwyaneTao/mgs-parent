package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryOrderStatisticsDTO implements Serializable{

    /**
     * 操作人
     */
    private String operator;

    /**
     * 商家编码
     */
    private String companyCode;
}
