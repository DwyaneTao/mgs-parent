package com.mgs.product.dto;

import com.mgs.common.BaseRequest;
import lombok.Data;

/**
 * @author py
 * @date 2019/7/19 15:15
 **/
@Data
public class RoomCharterQueryQuestDTO  extends BaseRequest {
    /**
     * 包房Id
     */
    private  Integer roomCharterId;
    /**
     * 包房批次编码
     */
    private  String roomCharterCode;
    /**
     *供应商编码
     */
    private  String  supplierCode;
    /**
     * 酒店Id
     */
    private  Integer hotelId;
    /**
     * 剩余间夜数
     */
    private  Integer  remainingNightQty;
    /**
     * 包房批次名
     */
    private  String roomCharterName;
    /**
     * 即将到期天数
     */
     private  Integer remainingDays;
    /**
     * 运营商编码
     */
    private  String  companyCode;
    /**
     * 到期状态
     */
    private  String overdueStatus;
}
