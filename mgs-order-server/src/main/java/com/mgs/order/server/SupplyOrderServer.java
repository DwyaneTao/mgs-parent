package com.mgs.order.server;

import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.request.AddProductDTO;
import com.mgs.order.remote.request.ModifySupplierOrderCodeDTO;
import com.mgs.order.remote.request.ModifySupplyOrderSettlementTypeDTO;
import com.mgs.order.remote.request.ModifySupplyProductDTO;
import com.mgs.order.remote.request.SaveSupplyAttachmentDTO;
import com.mgs.order.remote.request.SaveSupplyResultDTO;
import com.mgs.order.remote.request.SendToSupplierDTO;
import com.mgs.order.remote.request.SupplyAttachmentIdDTO;
import com.mgs.order.remote.request.SupplyProductIdDTO;
import com.mgs.order.remote.response.PrintSupplyOrderDTO;
import com.mgs.order.service.SupplyOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class SupplyOrderServer {

    @Autowired
    private SupplyOrderService supplyOrderService;

    /**
     * 修改产品
     * @param request
     * @return
     */
    @PostMapping("/order/modifySupplyProduct")
    Response modifySupplyProduct(@RequestBody ModifySupplyProductDTO request){
        Response response = new Response();
        try{
            response=supplyOrderService.modifySupplyProduct(request);
        } catch (Exception e) {
            log.error("modifySupplyProduct server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 添加产品
     * @param request
     * @return
     */
    @PostMapping("/order/addProduct")
    Response addProduct(@RequestBody AddProductDTO request){
        Response response = new Response();
        try{
            response=supplyOrderService.addProduct(request);
        } catch (Exception e) {
            log.error("addProduct server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 删除产品
     * @param request
     * @return
     */
    @PostMapping("/order/deleteProduct")
    Response deleteProduct(@RequestBody SupplyProductIdDTO request){
        Response response = new Response();
        try{
            response=supplyOrderService.deleteProduct(request);
        } catch (Exception e) {
            log.error("deleteProduct server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 发单
     * @param request
     * @return
     */
    @PostMapping("/order/sendToSupplier")
    Response sendToSupplier(@RequestBody SendToSupplierDTO request){
        Response response = new Response();
        try{
            response=supplyOrderService.sendToSupplier(request);
        } catch (Exception e) {
            log.error("sendToSupplier server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 保存供货单结果
     * @param request
     * @return
     */
    @PostMapping("/order/saveSupplyResult")
    Response saveSupplyResult(@RequestBody SaveSupplyResultDTO request){
        Response response = new Response();
        try{
            response=supplyOrderService.saveSupplyResult(request);
        } catch (Exception e) {
            log.error("saveSupplyResult server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改供货单结算方式
     * @param request
     * @return
     */
    @PostMapping("/order/modifySupplyOrderSettlementType")
    Response modifySupplyOrderSettlementType(@RequestBody ModifySupplyOrderSettlementTypeDTO request){
        Response response = new Response();
        try{
            response=supplyOrderService.modifySupplyOrderSettlementType(request);
        } catch (Exception e) {
            log.error("modifySupplyOrderSettlementType server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 上传供货单附件
     * @param request
     * @return
     */
    @PostMapping("/order/saveSupplyAttachment")
    Response saveSupplyAttachment(@RequestBody SaveSupplyAttachmentDTO request){
        Response response = new Response();
        try{
            response=supplyOrderService.saveSupplyAttachment(request);
        } catch (Exception e) {
            log.error("saveSupplyAttachment server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 删除供货单附件
     * @param request
     * @return
     */
    @PostMapping("/order/deleteSupplyAttachment")
    Response deleteSupplyAttachment(@RequestBody SupplyAttachmentIdDTO request){
        Response response = new Response();
        try{
            response=supplyOrderService.deleteSupplyAttachment(request);
        } catch (Exception e) {
            log.error("deleteSupplyAttachment server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改供应商订单号
     * @param request
     * @return
     */
    @PostMapping("/order/modifySupplierOrderCode")
    Response modifySupplierOrderCode(@RequestBody ModifySupplierOrderCodeDTO request){
        Response response = new Response();
        try{
            response=supplyOrderService.modifySupplierOrderCode(request);
        } catch (Exception e) {
            log.error("modifySupplierOrderCode server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 批量打印供应单
     * @param request
     * @return
     */
    @PostMapping("/order/batchPrintSupplierOrder")
    Response batchPrintSupplierOrder(@RequestBody Map<String,String> request){
        Response response = new Response();
        try{
            response=supplyOrderService.batchPrintSupplierOrder(request);

        } catch (Exception e) {
            log.error("batchPrintSupplierOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
