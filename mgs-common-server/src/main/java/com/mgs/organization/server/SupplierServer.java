package com.mgs.organization.server;

import com.mgs.common.BaseController;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.organization.remote.dto.QuerySupplierListDTO;
import com.mgs.organization.remote.dto.SupplierAddDTO;
import com.mgs.organization.remote.dto.SupplierListRequest;
import com.mgs.organization.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author py
 * @date 2019/6/19 22:13
 **/
@RestController
@Slf4j
@RequestMapping("/supplier")
public class SupplierServer{
    @Autowired
    private SupplierService supplierService;

    /**
     * 新增供应商
     */
    @PostMapping("/addSupplier")
    Response addSupplier(@RequestBody SupplierAddDTO request){
        Response response = new Response();
        try{
            response= supplierService.addSupplier(request);
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
    @PostMapping("/modifySupplierStatus")
    Response modifySupplierStatus(@RequestBody SupplierAddDTO request){
        Response response = new Response();
        try{
            response = supplierService.modifySupplierStatus(request);
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
    @PostMapping("/modifySupplier")
    Response modifySupplier(@RequestBody SupplierAddDTO request){
        Response response = new Response();
        try{
            response= supplierService.modifySupplier(request);
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
    @PostMapping("/querySupplierDetail")
    Response querySupplierDetail(@RequestBody SupplierAddDTO request){
        Response response = new Response();
        try{
            response= supplierService.querySupplierDetail(request);
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
            PaginationSupportDTO<QuerySupplierListDTO> paginationSupportDTO=supplierService.querySupplierList(request);
            response.setResult(ResultCodeEnum.SUCCESS.code);
            response.setModel(paginationSupportDTO);
        } catch (Exception e) {
            log.error("querySupplierList server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

}
