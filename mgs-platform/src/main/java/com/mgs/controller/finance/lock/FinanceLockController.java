package com.mgs.controller.finance.lock;

import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.remote.lock.FinanceLockRemote;
import com.mgs.finance.remote.lock.request.FinanceLockOrderDTO;
import com.mgs.finance.remote.lock.request.FinanceLockSupplyOrderDTO;
import com.mgs.finance.remote.lock.request.QueryOrderFinanceLockListDTO;
import com.mgs.finance.remote.lock.request.QuerySupplyOrderFinanceLockListDTO;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/finance/lock")
public class FinanceLockController extends BaseController {

    @Autowired
    private FinanceLockRemote financeLockRemote;

    /**
     * 财务订单锁查询
     */
    @RequestMapping(value = "/queryOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderList(@RequestBody QueryOrderFinanceLockListDTO request) {
        Response response=null;
        try {
            request.setCompanyCode(super.getCompanyCode());
            if(StringUtil.isValidString(request.getLockStatus()) && request.getLockStatus().equals("-1")){
                request.setLockStatus(null);
            }
            response=financeLockRemote.queryOrderList(request);
            log.info("financeLockRemote.queryOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("financeLockRemote.queryOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 财务订单加锁
     */
    @RequestMapping(value = "/lockOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response lockOrder(@RequestBody FinanceLockOrderDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=financeLockRemote.lockOrder(request);
            log.info("financeLockRemote.lockOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("financeLockRemote.lockOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 财务供货单锁查询
     */
    @RequestMapping(value = "/querySupplyOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response querySupplyOrderList(@RequestBody QuerySupplyOrderFinanceLockListDTO request) {
        Response response=null;
        try {
            request.setCompanyCode(super.getCompanyCode());
            if(StringUtil.isValidString(request.getLockStatus()) && request.getLockStatus().equals("-1")){
                request.setLockStatus(null);
            }
            response=financeLockRemote.querySupplyOrderList(request);
            log.info("financeLockRemote.querySupplyOrderList result:"+response.getResult());
        } catch (Exception e) {
            log.error("financeLockRemote.querySupplyOrderList 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 财务订单加锁
     */
    @RequestMapping(value = "/lockSupplyOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response lockSupplyOrder(@RequestBody FinanceLockSupplyOrderDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=financeLockRemote.lockSupplyOrder(request);
            log.info("financeLockRemote.lockSupplyOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("financeLockRemote.lockSupplyOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
