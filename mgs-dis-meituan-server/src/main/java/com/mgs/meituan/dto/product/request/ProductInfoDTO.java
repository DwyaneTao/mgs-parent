package com.mgs.meituan.dto.product.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductInfoDTO {

    /**
     * 酒店房型编码
     */
    private String roomType;

    /**
     * 酒店房型名称
     */
    private String roomName;

    /**
     * 0表示无早，1表示单早，2表示双早。
     * 默认值0
     */
    private Integer breakfastNum;

    /**
     * 最小预订间数至少为2的产品
     */
    private Boolean isTeamRoom;

    /**
     * 价格日历
     */
    private List<InventoryPriceDTO> inventoryPrice;

    /**
     * 销售规则
     */
    private List<RuleSyncDTO> rule;

}
