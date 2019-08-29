package com.mgs.order.server;

import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.request.AddManualOrderDTO;
import com.mgs.order.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BookingServer {

    @Autowired
    private BookingService bookingService;

    /**
     * 新建手工单
     * @param request
     * @return
     */
    @PostMapping("/order/addManualOrder")
    Response addManualOrder(@RequestBody AddManualOrderDTO request){
        Response response = new Response();
        try{
            response=bookingService.addManualOrder(request);
        } catch (Exception e) {
            log.error("addManualOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 新建OTA订单
     * @param request
     * @return
     */
    @PostMapping("/order/addOTAOrder")
    Response addOTAOrder(@RequestBody AddManualOrderDTO request){
        Response response = new Response();
        try{
            response=bookingService.addOTAOrder(request);
        } catch (Exception e) {
            log.error("addOTAOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
