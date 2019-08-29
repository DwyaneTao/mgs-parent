package com.mgs.meituan.dto.hotel;

import lombok.Data;

@Data
public class BaseRequestDTO {

    /**
     * 内部供应商ID
     */
    private String belongPartnerId;

    /**
     * 活动码
     */
    private String activeCode;

    /**
     * poi Id
     */
    private String poiId;

    /**
     * 房型编号
     */
    private String roomType;

    /**
     * 早餐数量
     */
    private Integer breakfastNum;
}
