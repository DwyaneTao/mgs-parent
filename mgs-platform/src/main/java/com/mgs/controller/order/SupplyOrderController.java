package com.mgs.controller.order;

import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.controller.util.upload.FileUploadUtil;
import com.mgs.controller.util.upload.UploadFileConfig;
import com.mgs.controller.util.upload.UploadResponseDTO;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.enums.SendingTypeEnum;
import com.mgs.order.remote.SupplyOrderRemote;
import com.mgs.order.remote.request.AddProductDTO;
import com.mgs.order.remote.request.ModifySupplierOrderCodeDTO;
import com.mgs.order.remote.request.ModifySupplyOrderSettlementTypeDTO;
import com.mgs.order.remote.request.ModifySupplyProductDTO;
import com.mgs.order.remote.request.SaveSupplyAttachmentDTO;
import com.mgs.order.remote.request.SaveSupplyResultDTO;
import com.mgs.order.remote.request.SendToSupplierDTO;
import com.mgs.order.remote.request.SupplyAttachmentIdDTO;
import com.mgs.order.remote.request.SupplyProductIdDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/order")
public class SupplyOrderController extends BaseController {

    @Autowired
    private SupplyOrderRemote supplyOrderRemote;

    @Autowired
    private UploadFileConfig uploadFileConfig;

    @RequestMapping(value = "/modifySupplyProduct",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifySupplyProduct(@RequestBody ModifySupplyProductDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=supplyOrderRemote.modifySupplyProduct(request);
            log.info("supplyOrderRemote.modifySupplyProduct result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderRemote.modifySupplyProduct 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/addProduct",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response addProduct(@RequestBody AddProductDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=supplyOrderRemote.addProduct(request);
            log.info("supplyOrderRemote.addProduct result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderRemote.addProduct 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/deleteProduct",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response deleteProduct(@RequestBody SupplyProductIdDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            response=supplyOrderRemote.deleteProduct(request);
            log.info("supplyOrderRemote.deleteProduct result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderRemote.deleteProduct 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/sendToSupplier",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response sendToSupplier(@RequestBody SendToSupplierDTO request) {
        Response response=null;
        try {
            request.setSendingType(SendingTypeEnum.EBK.key);
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=supplyOrderRemote.sendToSupplier(request);
            log.info("supplyOrderRemote.sendToSupplier result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderRemote.sendToSupplier 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/saveSupplyResult",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response saveSupplyResult(@RequestBody SaveSupplyResultDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=supplyOrderRemote.saveSupplyResult(request);
            log.info("supplyOrderRemote.saveSupplyResult result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderRemote.saveSupplyResult 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/modifySupplyOrderSettlementType",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifySupplyOrderSettlementType(@RequestBody ModifySupplyOrderSettlementTypeDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=supplyOrderRemote.modifySupplyOrderSettlementType(request);
            log.info("supplyOrderRemote.modifySupplyOrderSettlementType result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderRemote.modifySupplyOrderSettlementType 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/uploadSupplyAttachment",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response uploadSupplyAttachment(@RequestBody SaveSupplyAttachmentDTO saveSupplyAttachmentDTO) {
        Response response=null;
        try {
            if (null != saveSupplyAttachmentDTO && null != saveSupplyAttachmentDTO.getSupplyOrderId()
                    && null != saveSupplyAttachmentDTO.getUrl()) {
                saveSupplyAttachmentDTO.setOperator(getUserName());
                saveSupplyAttachmentDTO.setOrderOwnerName(super.getLoginName());
                response=supplyOrderRemote.saveSupplyAttachment(saveSupplyAttachmentDTO);
            }else {
                response = new Response(ResultCodeEnum.SUCCESS.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("supplyOrderRemote.uploadSupplyAttachment 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/deleteSupplyAttachment",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response deleteSupplyAttachment(@RequestBody SupplyAttachmentIdDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=supplyOrderRemote.deleteSupplyAttachment(request);
            log.info("supplyOrderRemote.deleteSupplyAttachment result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderRemote.deleteSupplyAttachment 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/modifySupplierOrderCode",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifySupplierOrderCode(@RequestBody ModifySupplierOrderCodeDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=supplyOrderRemote.modifySupplierOrderCode(request);
            log.info("supplyOrderRemote.modifySupplierOrderCode result:"+response.getResult());
        } catch (Exception e) {
            log.error("supplyOrderRemote.modifySupplierOrderCode 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/batchPrintSupplierOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response batchPrintSupplierOrder(@RequestBody Map<String,String> request) {
        Response response=null;
        try {
            response=supplyOrderRemote.batchPrintSupplierOrder(request);
            log.info("orderQueryRemote.batchPrintSupplierOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderQueryRemote.batchPrintSupplierOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
