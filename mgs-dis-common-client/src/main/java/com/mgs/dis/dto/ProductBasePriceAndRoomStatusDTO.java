package com.mgs.dis.dto;

import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 产品底价和房态
 */
@Data
public class ProductBasePriceAndRoomStatusDTO {

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 售价日期
     */
    private String saleDate;

    /**
     * 底价
     */
    private BigDecimal basePrice;


    /**
     * 房态(1开房 0关房)
     */
    private Integer roomStatus;

    /**
     * 售罄是否可超(1可超 0不可超)
     */
    private Integer overDraftStatus;

    /**
     * 配额数
     */
    private Integer quota;

    /**
     * 剩余配额数
     */
    private Integer remainingQuota;

    /**
     * redisKey
     */
    private String redisKey;

}
