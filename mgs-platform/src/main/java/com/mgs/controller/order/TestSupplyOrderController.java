package com.mgs.controller.order;

import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.request.AddProductDTO;
import com.mgs.order.remote.request.ModifySupplierOrderCodeDTO;
import com.mgs.order.remote.request.ModifySupplyOrderSettlementTypeDTO;
import com.mgs.order.remote.request.ModifySupplyProductDTO;
import com.mgs.order.remote.request.SaveSupplyResultDTO;
import com.mgs.order.remote.request.SendToSupplierDTO;
import com.mgs.order.remote.request.SupplyAttachmentIdDTO;
import com.mgs.order.remote.request.SupplyProductIdDTO;
import com.mgs.order.remote.response.SupplyAttachmentDTO;
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
public class TestSupplyOrderController {

    @RequestMapping(value = "/modifySupplyProduct",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifySupplyProduct(@RequestBody ModifySupplyProductDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/addProduct",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response addProduct(@RequestBody AddProductDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/deleteProduct",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response deleteProduct(@RequestBody SupplyProductIdDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/sendToSupplier",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response sendToSupplier(@RequestBody SendToSupplierDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/saveSupplyResult",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response saveSupplyResult(@RequestBody SaveSupplyResultDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/modifySupplyOrderSettlementType",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifySupplyOrderSettlementType(@RequestBody ModifySupplyOrderSettlementTypeDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/uploadSupplyAttachment")
    @ResponseBody
    public Response uploadSupplyAttachment(@RequestParam("file") MultipartFile file, @RequestParam("supplyOrderId") Integer supplyOrderId) {
        SupplyAttachmentDTO supplyAttachmentDTO=new SupplyAttachmentDTO();
        supplyAttachmentDTO.setSupplyAttachmentId(32321);
        supplyAttachmentDTO.setName("aaaaaa");
        supplyAttachmentDTO.setUrl("http://www.aa.com/aaaaaa.jpg");
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(supplyAttachmentDTO);
        return response;
    }

    @RequestMapping(value = "/deleteSupplyAttachment",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response deleteSupplyAttachment(@RequestBody SupplyAttachmentIdDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @RequestMapping(value = "/modifySupplierOrderCode",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response modifySupplierOrderCode(@RequestBody ModifySupplierOrderCodeDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }
}
