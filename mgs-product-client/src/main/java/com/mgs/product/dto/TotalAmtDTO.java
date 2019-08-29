package com.mgs.product.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author py
 * @date 2019/7/17 10:47
 * 根据日期查询产品所对应的底价
 **/
@Data
public class TotalAmtDTO {
    /**
     * 产品Id
     */
    private  Integer productId;
    /**
     * 底价
     */
    private BigDecimal basePrice;
    /**
     * 日期
     */
    private Date saleDate;
}
