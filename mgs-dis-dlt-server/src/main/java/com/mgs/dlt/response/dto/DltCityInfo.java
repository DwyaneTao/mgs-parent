package com.mgs.dlt.response.dto;

import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 21:55
 * @Description:
 */
@Data
public class DltCityInfo {
    private int cityID;
    private String cityName;
    private String cityCode;
    private int provinceID;
    private String provinceName;
    private int countryID;
    private String countryName;
}
