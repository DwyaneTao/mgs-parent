package com.mgs.product.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author py
 * @date 2019/7/17 16:07
 **/
@Data
public class ProductPriceQueryRequestDTO {
    /**
     * 产品Id
     */
    private  Integer productId;
    /**
     * 入住开始时间
     */
    private Date startDate;
    /**
     * 离开时间
     */
    private  Date  endDate;
    /**
     * 运营商编码
     */
    private  String companyCode;
    /**
     * 销售渠道（B2B,B2C,ctrip,qunar,feizhu,meituan,tcyl）
     */
    private String channelCode;
}
