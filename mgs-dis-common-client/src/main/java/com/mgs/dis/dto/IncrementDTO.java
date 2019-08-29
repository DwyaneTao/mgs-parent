package com.mgs.dis.dto;

import lombok.Data;

import java.util.List;

/**
 * 增量
 */
@Data
public class IncrementDTO {

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 增量list
     */
    private List<String> saleDate;

    /**
     * 商家编码
     */
    private String companyCode;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 增量类型。详情见com.mgs.enums.IncrementTypeEnum
     */
    private Integer type;
}
