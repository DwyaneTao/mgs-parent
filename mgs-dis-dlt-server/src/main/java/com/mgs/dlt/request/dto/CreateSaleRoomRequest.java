package com.mgs.dlt.request.dto;

import com.mgs.dlt.request.base.BaseRequest;
import com.mgs.dlt.request.base.Requestor;
import lombok.Data;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/8/15 17:03
 * @Description:售卖房型
 */
@Data
public class CreateSaleRoomRequest extends BaseRequest {

    private Requestor requestor;
    private int supplierId;
    private int hotelId;
    private int basicRoomTypeId;
    private int roomTypeId;
    private int relationRoomId;
    private int purchaseSourceID;
    private List<Integer> rateCodePropertyValueIDList;
    private int priceType;
    private String applicabilityInfo;
    private List<Integer> applicabilityList;
}
