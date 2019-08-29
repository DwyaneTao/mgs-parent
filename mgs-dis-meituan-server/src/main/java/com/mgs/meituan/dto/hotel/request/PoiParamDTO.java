package com.mgs.meituan.dto.hotel.request;

import lombok.Data;

/**
 * 城市基本数据
 */
@Data
public class PoiParamDTO {

    /**
     * 内部供应商id
     */
    private String belongPartnerIds;

    /**
     * 对接方POI id
     */
    private String poild;

    /**
     * POI名称
     */
    private String pointName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private Integer cityId;

    /**
     * 区域 1：内地 2：海外
     */
    private Integer regionType;
}
