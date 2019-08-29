package com.mgs.dlt.request.dto;

import lombok.Data;

/**
 *   2018/4/8.
 */
@Data
public class RoomInventoryModel {

    private Integer preservedQuantity = 0;
    private Integer unPreservedQuantity = 0;
    private Integer autoCloseRoom = 1;
    private String channel;

}
