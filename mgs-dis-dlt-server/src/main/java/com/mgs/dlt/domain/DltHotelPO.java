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
@Table(name = "t_dis_dlt_hotel")
public class DltHotelPO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer cityID;

    private String cityName;

    private Integer masterHotelID;

    private Integer hotelID;

    private String hotelName;

    private String hotelEName;

    private Integer cBookable;

    private Integer qBookable;

    private Integer channelaBookable;

    private Integer b2bBookable;

    private String cReserveTime;

    private Integer cReserveDay;

    private String qReserveTime;

    private Integer qReserveDay;
}
