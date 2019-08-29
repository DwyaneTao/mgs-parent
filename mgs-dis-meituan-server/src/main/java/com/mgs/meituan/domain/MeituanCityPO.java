package com.mgs.meituan.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "t_dis_meituan_city")
public class MeituanCityPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 美团城市Id
     */
    private Integer meituanCityId;

    /**
     * 美团城市名称
     */
    private String meituanCityName;

    /**
     * mgHotel城市编码
     */
    private String cityCode;

    /**
     * mgHotel城市名称
     */
    private String cityName;

    /**
     * 有效性
     */
    private Integer active;
}
