package com.mgs.meituan.dto.city.request;

import lombok.Data;

/**
 * 美团城市信息
 */
@Data
public class MeituanCityDTO {

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 城市编码
     */
    private Integer cityCode;
}
