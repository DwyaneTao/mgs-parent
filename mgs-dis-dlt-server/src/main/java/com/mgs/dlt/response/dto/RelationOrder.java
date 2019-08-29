package com.mgs.dlt.response.dto;

import lombok.Data;

/**
 *   2018/4/8.
 */
@Data
public class RelationOrder {

    private String lastDltOrderId;
    private String nextDltOrderId;
    private String lastOrderId;
    private String nextOrderId;
    private String lastConfirmType;
    private String nextConfirmType;

}
