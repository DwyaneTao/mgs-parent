package com.mgs.finance.lock.server;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.lock.service.FinanceLockService;
import com.mgs.finance.remote.lock.request.FinanceLockOrderDTO;
import com.mgs.finance.remote.lock.request.FinanceLockSupplyOrderDTO;
import com.mgs.finance.remote.lock.request.QueryOrderFinanceLockListDTO;
import com.mgs.finance.remote.lock.request.QuerySupplyOrderFinanceLockListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FinanceLockServer {

    @Autowired
    private FinanceLockService financeLockService;

    /**
     * 财务订单锁查询
     */
    @PostMapping("/finance/lock/queryOrderList")
    public Response queryOrderList(@RequestBody QueryOrderFinanceLockListDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=financeLockService.queryOrderList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("queryOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 财务订单加锁
     */
    @PostMapping("/finance/lock/lockOrder")
    public Response lockOrder(@RequestBody FinanceLockOrderDTO request){
        Response response = new Response();
        try{
            response=financeLockService.lockOrder(request);
        } catch (Exception e) {
            log.error("queryOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 财务供货单锁查询
     */
    @PostMapping("/finance/lock/querySupplyOrderList")
    public Response querySupplyOrderList(@RequestBody QuerySupplyOrderFinanceLockListDTO request){
        Response response = new Response();
        try{
            PaginationSupportDTO paginationSupportDTO=financeLockService.querySupplyOrderList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("querySupplyOrderList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 财务订单加锁
     */
    @PostMapping("/finance/lock/lockSupplyOrder")
    public Response lockSupplyOrder(@RequestBody FinanceLockSupplyOrderDTO request){
        Response response = new Response();
        try{
            response=financeLockService.lockSupplyOrder(request);
        } catch (Exception e) {
            log.error("lockSupplyOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
