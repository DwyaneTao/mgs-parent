package com.mgs.dlt.service;


import com.mgs.dlt.response.dto.DltOrderInfo;

/**
 *   2018/2/28.
 */
public interface DltHotelOrderService {

    void createOrder(DltOrderInfo dltOrderInfo, String merchantCode);

    void updateOrderHandleResult(String dltOrderId, Boolean successed, String msg);

}
