package com.mgs.finance.workorder.server;

import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.workorder.service.FinanceNofityService;
import com.mgs.finance.remote.workorder.request.NotifyCollectionDTO;
import com.mgs.finance.remote.workorder.request.NotifyPaymentDTO;
import com.mgs.finance.remote.workorder.response.NotificationLogDTO;
import com.mgs.finance.remote.workorder.request.BusinessCodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class FinanceNofityServer {

    @Autowired
    private FinanceNofityService financeNofityService;

    /**
     * 通知收款
     */
    @PostMapping("/finance/notify/notifyCollection")
    public Response notifyCollection(@RequestBody NotifyCollectionDTO request){
        Response response = new Response();
        try{
            response=financeNofityService.notifyCollection(request);
        } catch (Exception e) {
            log.error("notifyCollection server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知付款
     */
    @PostMapping("/finance/notify/notifyPayment")
    public Response notifyPayment(@RequestBody NotifyPaymentDTO request){
        Response response = new Response();
        try{
            response=financeNofityService.notifyPayment(request);
        } catch (Exception e) {
            log.error("notifyPayment server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 通知记录查询
     */
    @PostMapping("/finance/notify/financeNotificationLogList")
    public Response financeNotificationLogList(@RequestBody BusinessCodeDTO request){
        Response response = new Response();
        try{
            List<NotificationLogDTO> notificationLogDTOList=financeNofityService.financeNotificationLogList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(notificationLogDTOList);
        } catch (Exception e) {
            log.error("financeNotificationLogList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
