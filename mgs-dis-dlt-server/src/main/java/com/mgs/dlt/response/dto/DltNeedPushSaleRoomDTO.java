package com.mgs.dlt.response.dto;

import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/8/17 18:05
 * @Description:需要推送的售卖房型
 */
@Data
public class DltNeedPushSaleRoomDTO {
    private Integer hotelId;
    private String hotelName;
    private Integer roomId;
    private String roomName;
    private Integer productId;
    private String productName;
    private Integer breakfastQty;
}
