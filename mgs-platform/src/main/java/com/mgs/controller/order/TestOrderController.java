package com.mgs.controller.order;

import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.request.AddManualOrderDTO;
import com.mgs.order.remote.request.AddRemarkDTO;
import com.mgs.order.remote.request.CancelOrderDTO;
import com.mgs.order.remote.request.ConfirmOrderDTO;
import com.mgs.order.remote.request.HandleOrderRequestDTO;
import com.mgs.order.remote.request.MarkOrderDTO;
import com.mgs.order.remote.request.ModifyGuestDTO;
import com.mgs.order.remote.request.ModifyOrderRoomDTO;
import com.mgs.order.remote.request.ModifyOrderSettlementTypeDTO;
import com.mgs.order.remote.request.ModifySalePriceDTO;
import com.mgs.order.remote.request.ModifySpecialRequirementDTO;
import com.mgs.order.remote.request.OrderAttachmentIdDTO;
import com.mgs.order.remote.request.OrderIdDTO;
import com.mgs.order.remote.response.OrderAttachmentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping(value = "/test/order")
public class TestOrderController {

    @RequestMapping(value = "/addManualOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response addManualOrder(@RequestBody AddManualOrderDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel("H2019042911112");
        return response;
    }

    @RequestMapping(value = "/confirmOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response confirmOrder(@RequestBody ConfirmOrderDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/cancelOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response cancelOrder(@RequestBody CancelOrderDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/modifyGuest",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifyGuest(@RequestBody ModifyGuestDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/modifySpecialRequirement",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifySpecialRequirement(@RequestBody ModifySpecialRequirementDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/modifySalePrice",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifySalePrice(@RequestBody ModifySalePriceDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/addRemark",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response addRemark(@RequestBody AddRemarkDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/uploadOrderAttachment")
    @ResponseBody
    public Response uploadOrderAttachment(@RequestParam("file") MultipartFile file,@RequestParam("orderId") Integer orderId) {
        OrderAttachmentDTO orderAttachmentDTO=new OrderAttachmentDTO();
        orderAttachmentDTO.setOrderAttachmentId(32321);
        orderAttachmentDTO.setName("aaaaaa");
        orderAttachmentDTO.setUrl("http://www.aa.com/aaaaaa.jpg");
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(orderAttachmentDTO);
        return response;
    }

    @RequestMapping(value = "/deleteOrderAttachment",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response deleteOrderAttachment(@RequestBody OrderAttachmentIdDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/handleOrderRequest",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response handleOrderRequest(@RequestBody HandleOrderRequestDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/modifyOrderRoom",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifyOrderRoom(@RequestBody ModifyOrderRoomDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/modifyOrderSettlementType",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifyOrderSettlementType(@RequestBody ModifyOrderSettlementTypeDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/lockOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response lockOrder(@RequestBody OrderIdDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/markOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response markOrder(@RequestBody MarkOrderDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }
}
