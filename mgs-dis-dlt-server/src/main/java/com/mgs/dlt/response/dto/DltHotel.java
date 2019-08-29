package com.mgs.dlt.response.dto;

import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 22:01
 * @Description:
 */
@Data
public class DltHotel {

    private int cityID;
    private String cityName;
    private long masterHotelID;
    private int hotelID;
    private String hotelName;
    private String hotelEName;
    private int cBookable;
    private int qBookable;
    private int channelaBookable;
    private int b2bBookable;
    private String cReserveTime;
    private int cReserveDay;
    private String qReserveTime;
    private int qReserveDay;
}
