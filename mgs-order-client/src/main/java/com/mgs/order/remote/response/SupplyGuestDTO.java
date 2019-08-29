package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyGuestDTO implements Serializable{

    /**
     * 入住人
     */
    private String guest;

    /**
     * 是否选中：0未选中，1选中
     */
    private Integer selected;
}
