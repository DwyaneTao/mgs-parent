package com.mgs.order.service;

import com.mgs.common.Response;
import com.mgs.order.remote.request.AddManualOrderDTO;

public interface BookingService {

    /**
     * 新建手工单
     * @param request
     * @return
     */
    Response addManualOrder(AddManualOrderDTO request);

    /**
     * 新建OTA订单
     * @param request
     * @return
     */
    Response addOTAOrder(AddManualOrderDTO request);
}
