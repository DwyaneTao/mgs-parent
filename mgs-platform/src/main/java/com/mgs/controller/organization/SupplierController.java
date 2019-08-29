package com.mgs.controller.organization;

import com.mgs.common.BaseController;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.organization.remote.SupplierRemote;
import com.mgs.organization.remote.dto.QuerySupplierListDTO;
import com.mgs.organization.remote.dto.SupplierAddDTO;
import com.mgs.organization.remote.dto.SupplierListRequest;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

/**
 * @author py
 * @date 2019/7/2 15:30
 **/
@RestController
@Slf4j
@RequestMapping(value = "/supplier")
public class SupplierController extends BaseController {
    @Autowired
    private SupplierRemote supplierRemote;
    /**
     * 新增供应商
     */
    @RequestMapping(value = "/addSupplier",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response addSupplier(@RequestBody SupplierAddDTO request){
        Response response = new Response();
        try{
            if(request != null && request.getSupplierType()!=null
                    && StringUtil.isValidString(request.getSupplierName())
                    && StringUtil.isValidString(request.getAdminName())
                    &&StringUtil.isValidString(request.getAdminAccount())
                    &&StringUtil.isValidString(request.getAdminTel())
                    &&request.getSettlementType()!=null&&request.getPurchaseManagerId()!=null)
                    {
                request.setOrgDomain(getOrgDomain());
                request.setSupplierCode(super.getCompanyCode());
                request.setCreatedBy(super.getUserName());
                response= supplierRemote.addSupplier(request);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("addSupplier server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改供应商启用状态
     */
    @RequestMapping(value = "/modifySupplierStatus",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response modifySupplierStatus(@RequestBody SupplierAddDTO request){
        Response response = new Response();
        try{
            if(request != null && request.getSupplierId()!=null
                    &&request.getAvailableStatus()!=null)
            {
                request.setModifiedBy(getUserName());
                response = supplierRemote.modifySupplierStatus(request);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("modifySupplierStatus server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改供应商信息
     */
    @RequestMapping(value = "/modifySupplier",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response modifySupplier(@RequestBody SupplierAddDTO request){
        Response response = new Response();
        try{
            if(request != null &&request.getSupplierId()!=null&& request.getSupplierType()!=null
                    && StringUtil.isValidString(request.getSupplierName())
                    && StringUtil.isValidString(request.getAdminName())
                    &&StringUtil.isValidString(request.getAdminAccount())
                    &&StringUtil.isValidString(request.getAdminTel())
                    &&request.getSettlementType()!=null&&request.getPurchaseManagerId()!=null)
            {
                request.setModifiedBy(getUserName());
                request.setCreatedBy(getUserName());
                response= supplierRemote.modifySupplier(request);
            }else {
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }

        } catch (Exception e) {
            log.error("modifySupplier server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 供应商详情
     */
    @RequestMapping(value = "/querySupplierDetail",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    Response querySupplierDetail(@RequestBody SupplierAddDTO request){
        Response response = new Response();
        try{
            if (StringUtil.isValidString(request.getSupplierCode())){
            response= supplierRemote.querySupplierDetail(request);}
            else{
                response.setResult(0);
                response.setFailCode(ErrorCodeEnum.INVALID_INPUTPARAM.errorCode);
                response.setFailReason(ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("querySupplierDetail server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询供应商列表
     */
    @PostMapping("/querySupplierList")
    Response querySupplierList(@RequestBody SupplierListRequest request){
        Response response = new Response();
        try{
                request.setCompanyCode(getCompanyCode());
                if(request.getAvailableStatus()!=null){
                if(request.getAvailableStatus()==-1){
                    request.setAvailableStatus(null);
                }}
                response=supplierRemote.querySupplierList(request);

        } catch (Exception e) {
            log.error("querySupplierList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

}
