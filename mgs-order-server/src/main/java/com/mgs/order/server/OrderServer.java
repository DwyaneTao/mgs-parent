package com.mgs.order.server;

import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.request.AddOrderRequestDTO;
import com.mgs.order.remote.request.AddRemarkDTO;
import com.mgs.order.remote.request.CancelOrderDTO;
import com.mgs.order.remote.request.ConfirmOrderDTO;
import com.mgs.order.remote.request.HandleOrderRequestDTO;
import com.mgs.order.remote.request.LockOrderDTO;
import com.mgs.order.remote.request.MarkOrderDTO;
import com.mgs.order.remote.request.ModifyGuestDTO;
import com.mgs.order.remote.request.ModifyOrderRoomDTO;
import com.mgs.order.remote.request.ModifyOrderSettlementTypeDTO;
import com.mgs.order.remote.request.ModifyRoomDTO;
import com.mgs.order.remote.request.ModifySalePriceDTO;
import com.mgs.order.remote.request.ModifySpecialRequirementDTO;
import com.mgs.order.remote.request.OrderAttachmentIdDTO;
import com.mgs.order.remote.request.SaveOrderAttachmentDTO;
import com.mgs.order.remote.response.OrderDTO;
import com.mgs.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class OrderServer {

    @Autowired
    private OrderService orderService;

    /**
     * 确认订单
     * @param request
     * @return
     */
    @PostMapping("/order/confirmOrder")
    Response confirmOrder(@RequestBody ConfirmOrderDTO request){
        Response response = new Response();
        try{
            response=orderService.confirmOrder(request);
        } catch (Exception e) {
            log.error("confirmOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 取消订单
     * @param request
     * @return
     */
    @PostMapping("/order/cancelOrder")
    Response cancelOrder(@RequestBody CancelOrderDTO request){
        Response response = new Response();
        try{
            response=orderService.cancelOrder(request);
        } catch (Exception e) {
            log.error("cancelOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 订单取消申请
     * @param request
     * @return
     */
    @PostMapping("/order/addOrderRequest")
    public Response addOrderRequest(@RequestBody AddOrderRequestDTO request) {
        Response response = new Response();
        try{
            response=orderService.addOrderRequest(request);
        } catch (Exception e) {
            log.error("addOrderRequest server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改入住人
     * @param request
     * @return
     */
    @PostMapping("/order/modifyGuest")
    Response modifyGuest(@RequestBody ModifyGuestDTO request){
        Response response = new Response();
        try{
            response=orderService.modifyGuest(request);
        } catch (Exception e) {
            log.error("modifyGuest server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改房型
     * @param request
     * @return
     */
    @PostMapping("/order/modifyRoom")
    Response modifyRoom(@RequestBody ModifyRoomDTO request){
        Response response = new Response();
        try {
            int i = orderService.modifyRoom(request);

            response.setResult(1);
            response.setModel(i);
        }catch (Exception e){
            log.error("modifyRoom server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改特殊要求
     * @param request
     * @return
     */
    @PostMapping("/order/modifySpecialRequirement")
    Response modifySpecialRequirement(@RequestBody ModifySpecialRequirementDTO request){
        Response response = new Response();
        try{
            response=orderService.modifySpecialRequirement(request);
        } catch (Exception e) {
            log.error("modifySpecialRequirement server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改售价
     * @param request
     * @return
     */
    @PostMapping("/order/modifySalePrice")
    Response modifySalePrice(@RequestBody ModifySalePriceDTO request){
        Response response = new Response();
        try{
            response=orderService.modifySalePrice(request);
        } catch (Exception e) {
            log.error("modifySalePrice server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 添加备注
     * @param request
     * @return
     */
    @PostMapping("/order/addRemark")
    Response addRemark(@RequestBody AddRemarkDTO request){
        Response response = new Response();
        try{
            response=orderService.addRemark(request);
        } catch (Exception e) {
            log.error("addRemark server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 上传附件
     * @param request
     * @return
     */
    @PostMapping("/order/saveOrderAttachment")
    Response saveOrderAttachment(@RequestBody SaveOrderAttachmentDTO request){
        Response response = new Response();
        try{
            response=orderService.saveOrderAttachment(request);
        } catch (Exception e) {
            log.error("saveOrderAttachment server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 删除附件
     * @param request
     * @return
     */
    @PostMapping("/order/deleteOrderAttachment")
    Response deleteOrderAttachment(@RequestBody OrderAttachmentIdDTO request){
        Response response = new Response();
        try{
            response=orderService.deleteOrderAttachment(request);
        } catch (Exception e) {
            log.error("deleteOrderAttachment server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 处理订单请求
     * @param request
     * @return
     */
    @PostMapping("/order/handleOrderRequest")
    Response handleOrderRequest(@RequestBody HandleOrderRequestDTO request){
        Response response = new Response();
        try{
            response=orderService.handleOrderRequest(request);
        } catch (Exception e) {
            log.error("handleOrderRequest server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改订单房型
     * @param request
     * @return
     */
    @PostMapping("/order/modifyOrderRoom")
    Response modifyOrderRoom(@RequestBody ModifyOrderRoomDTO request){
        Response response = new Response();
        try{
            response=orderService.modifyOrderRoom(request);
        } catch (Exception e) {
            log.error("modifyOrderRoom server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改订单结算方式
     * @param request
     * @return
     */
    @PostMapping("/order/modifyOrderSettlementType")
    Response modifyOrderSettlementType(@RequestBody ModifyOrderSettlementTypeDTO request){
        Response response = new Response();
        try{
            response=orderService.modifyOrderSettlementType(request);
        } catch (Exception e) {
            log.error("modifyOrderSettlementType server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 解锁订单
     * @param request
     * @return
     */
    @PostMapping("/order/lockOrder")
    Response lockOrder(@RequestBody LockOrderDTO request){
        Response response = new Response();
        try{
            response=orderService.lockOrder(request);
        } catch (Exception e) {
            log.error("lockOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 标记订单
     * @param request
     * @return
     */
    @PostMapping("/order/markOrder")
    Response markOrder(@RequestBody MarkOrderDTO request){
        Response response = new Response();
        try{
            response=orderService.markOrder(request);
        } catch (Exception e) {
            log.error("addManualOrder server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 标记订单
     * @param request
     * @return
     */
    @PostMapping("/order/modifyChannelOrderCode")
    Response modifyChannelOrderCode(@RequestBody Map<String,String> request){
        Response response = new Response();
        try{
            response=orderService.modifyChannelOrderCode(request);
        } catch (Exception e) {
            log.error("modifyChannelOrderCode server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

}
