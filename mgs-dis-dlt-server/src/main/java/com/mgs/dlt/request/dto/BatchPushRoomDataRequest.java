package com.mgs.dlt.request.dto;

import com.mgs.dlt.request.base.BaseRequest;
import lombok.Data;

import java.util.List;

/**
 * 直连推送报价等数据接口-请求实体
 *   2018/4/8.
 */
@Data
public class BatchPushRoomDataRequest extends BaseRequest {

    private Integer supplierId;
    private Integer hotelId;
    private List<RoomDataEntity> roomDataEntitys;

}
