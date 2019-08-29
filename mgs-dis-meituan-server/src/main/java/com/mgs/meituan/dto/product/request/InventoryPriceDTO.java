package com.mgs.meituan.dto.product.request;

import lombok.Data;

/***
 * 价格日历
 */
@Data
public class InventoryPriceDTO {

    /**
     * 营业日期。
     * 等于或晚于当前日期且在60天内(即不接收60天后的数据)
     * 格式：yyyy-MM-dd 如2016-01-12
     */
    private String roomDate;

    /**
     * 美团售卖价格；
     * 单位为分，值为非负数
     */
    private Integer roomPrice;

    /**
     * 底价（结算价）。
     * 单位为分，值为非负数
     */
    private Integer basePrice;

    /**
     * 市场价（挂牌价）。
     * 单位为分，值为非负数，若marketPrice为空或不传，则其值等于美团售卖价格
     */
    private Integer marketPrice;

    /**
     * 房间剩余数量。
     * 值为非负数且小于999
     */
    private Integer roomNum;
}
