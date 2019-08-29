package com.mgs.dlt.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 21:38
 * @Description:
 */
@Data
@Table(name = "t_dis_dlt_city")
public class DltCityPO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer cityID;

    private String cityName;

    private String cityCode;

    private Integer provinceID;

    private String provinceName;

    private Integer countryID;

    private String countryName;
}
