package com.mgs.controller.order;

import com.mgs.common.BaseController;
import com.mgs.common.Response;
import com.mgs.controller.util.upload.FileUploadUtil;
import com.mgs.controller.util.upload.UploadFileConfig;
import com.mgs.controller.util.upload.UploadResponseDTO;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.BookingRemote;
import com.mgs.order.remote.OrderRemote;
import com.mgs.order.remote.request.AddManualOrderDTO;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
public class OrderController extends BaseController {

    @Autowired
    private BookingRemote bookingRemote;

    @Autowired
    private OrderRemote orderRemote;

    @Autowired
    private UploadFileConfig uploadFileConfig;

    @RequestMapping(value = "/addManualOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response addManualOrder(@RequestBody AddManualOrderDTO request) {
        Response response=null;
        try {
            request.setCompanyCode(getCompanyCode());
            request.setOperator(super.getUserName());
            response=bookingRemote.addManualOrder(request);
            log.info("bookingRemote.addManualOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("bookingRemote.addManualOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 修改房型
     * @param request
     * @return
     */
    @PostMapping("/modifyRoom")
    Response modifyRoom(@RequestBody ModifyRoomDTO request){
        Response response = new Response();
        try {
            request.setOperator(getUserName());
            request.setLoginName(getLoginName() );
            response = orderRemote.modifyRoom(request);
        }catch (Exception e){
            log.error("modifyRoom server error!",e);
            response.setResult(ResultCodeEnum.FAILURE.code);
            response.setFailCode(ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode);
            response.setFailReason(ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/addOTAOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response addOTAOrder(@RequestBody AddManualOrderDTO request) {
        Response response=null;
        try {
            request.setCompanyCode(getCompanyCode());
            request.setOperator(super.getUserName());
            response=bookingRemote.addOTAOrder(request);
            log.info("bookingRemote.addOTAOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("bookingRemote.addOTAOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/confirmOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response confirmOrder(@RequestBody ConfirmOrderDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            request.setCompanyCode(getCompanyCode());
            response=orderRemote.confirmOrder(request);
            log.info("orderRemote.confirmOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderRemote.confirmOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/cancelOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response cancelOrder(@RequestBody CancelOrderDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=orderRemote.cancelOrder(request);
            log.info("orderRemote.cancelOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderRemote.cancelOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/modifyGuest",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifyGuest(@RequestBody ModifyGuestDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=orderRemote.modifyGuest(request);
            log.info("orderRemote.modifyGuest result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderRemote.modifyGuest 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/modifySpecialRequirement",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifySpecialRequirement(@RequestBody ModifySpecialRequirementDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=orderRemote.modifySpecialRequirement(request);
            log.info("orderRemote.modifySpecialRequirement result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderRemote.modifySpecialRequirement 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/modifySalePrice",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifySalePrice(@RequestBody ModifySalePriceDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=orderRemote.modifySalePrice(request);
            log.info("orderRemote.modifySalePrice result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderRemote.modifySalePrice 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/addRemark",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response addRemark(@RequestBody AddRemarkDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=orderRemote.addRemark(request);
            log.info("orderRemote.addRemark result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderRemote.addRemark 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/uploadOrderAttachment",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response uploadOrderAttachment(@RequestBody SaveOrderAttachmentDTO saveOrderAttachmentDTO) {
        Response response=null;
        try {
            if (null != saveOrderAttachmentDTO && null != saveOrderAttachmentDTO.getOrderId()
                    && null != saveOrderAttachmentDTO.getUrl()) {
                saveOrderAttachmentDTO.setOperator(getUserName());
                saveOrderAttachmentDTO.setOrderOwnerName(super.getLoginName());
                response=orderRemote.saveOrderAttachment(saveOrderAttachmentDTO);
            }else {
                response = new Response(ResultCodeEnum.SUCCESS.code,ErrorCodeEnum.INVALID_INPUTPARAM.errorCode,ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        } catch (Exception e) {
            log.error("orderRemote.saveOrderAttachment 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/deleteOrderAttachment",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response deleteOrderAttachment(@RequestBody OrderAttachmentIdDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=orderRemote.deleteOrderAttachment(request);
            log.info("orderRemote.deleteOrderAttachment result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderRemote.deleteOrderAttachment 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/handleOrderRequest",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response handleOrderRequest(@RequestBody HandleOrderRequestDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=orderRemote.handleOrderRequest(request);
            log.info("orderRemote.handleOrderRequest result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderRemote.handleOrderRequest 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/modifyOrderRoom",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifyOrderRoom(@RequestBody ModifyOrderRoomDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=orderRemote.modifyOrderRoom(request);
            log.info("orderRemote.modifyOrderRoom result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderRemote.modifyOrderRoom 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/modifyOrderSettlementType",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifyOrderSettlementType(@RequestBody ModifyOrderSettlementTypeDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=orderRemote.modifyOrderSettlementType(request);
            log.info("orderRemote.modifyOrderSettlementType result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderRemote.modifyOrderSettlementType 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/lockOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response lockOrder(@RequestBody LockOrderDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=orderRemote.lockOrder(request);
            log.info("orderRemote.lockOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderRemote.lockOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/markOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response markOrder(@RequestBody MarkOrderDTO request) {
        Response response=null;
        try {
            request.setOperator(super.getUserName());
            request.setOrderOwnerName(super.getLoginName());
            response=orderRemote.markOrder(request);
            log.info("orderRemote.markOrder result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderRemote.markOrder 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @RequestMapping(value = "/modifyChannelOrderCode",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifyChannelOrderCode(@RequestBody Map<String,String> request) {
        Response response=null;
        try {
            request.put("operator",super.getUserName());
            request.put("orderOwnerName",super.getLoginName());
            response=orderRemote.modifyChannelOrderCode(request);
            log.info("orderRemote.modifyChannelOrderCode result:"+response.getResult());
        } catch (Exception e) {
            log.error("orderRemote.modifyChannelOrderCode 接口异常",e);
            response=new Response(ResultCodeEnum.FAILURE.code,null, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }
}
