package com.mgs.fuzzyquery.dto;

import com.mgs.common.BaseRequest;
import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/7/2 12:01
 * @Description:模糊查询查询条件
 */
 @Data
public class FuzzyQueryDTO extends BaseRequest {

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 酒店Id
     */
    private Integer hotelId;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 分销商名称
     */
    private String agentName;

    /**
     * 房型Id
     */
    private Integer roomId;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 运营商编码
     */
    private String companyCode;

    /**
     * 客户类型
     */
    private String agentType;
}
