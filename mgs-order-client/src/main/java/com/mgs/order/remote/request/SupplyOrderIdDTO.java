package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SupplyOrderIdDTO implements Serializable{

    /**
     * 供货单id
     */
    private Integer supplyOrderId;
}
