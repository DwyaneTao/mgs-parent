package com.mgs.order.remote.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyOrderIdListDTO implements Serializable{

    /**
     * 供货单idList
     */
    private List<Integer> supplyOrderIdList;
}
