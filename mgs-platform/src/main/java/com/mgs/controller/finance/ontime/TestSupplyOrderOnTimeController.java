package com.mgs.controller.finance.ontime;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.request.NotifyCollectionOfSupplyOrderDTO;
import com.mgs.order.remote.request.NotifyCollectionOfSupplyOrderListDTO;
import com.mgs.order.remote.request.NotifyPaymentOfSupplyOrderDTO;
import com.mgs.order.remote.request.NotifyPaymentOfSupplyOrderListDTO;
import com.mgs.order.remote.request.OrderIdListDTO;
import com.mgs.order.remote.request.QueryOnTimeSupplyOrderListDTO;
import com.mgs.order.remote.response.OnTimeSupplyOrderDTO;
import com.mgs.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/test/finance/supplier")
public class TestSupplyOrderOnTimeController {

    /**
     * 单结订单查询
     */
    @RequestMapping(value = "/querySupplyOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response querySupplyOrderList(@RequestBody QueryOnTimeSupplyOrderListDTO request) {
        List<OnTimeSupplyOrderDTO> onTimeSupplyOrderDTOList=new ArrayList<>();
       /* for (int i=0;i<15;){
            onTimeSupplyOrderDTOList.add(new OnTimeSupplyOrderDTO(
                    i,
                    "S1805240000"+i,
                    "深圳环球旅行",
                    BigDecimal.valueOf(571l),
                    BigDecimal.valueOf(509l),
                    BigDecimal.valueOf(62l),
                    BigDecimal.valueOf(0l),
                    BigDecimal.valueOf(0l),
                    "2019-06-27",
                    28,
                    0
            ));
            onTimeSupplyOrderDTOList.add(new OnTimeSupplyOrderDTO(
                    i+1,
                    "S1805240000"+(i+1),
                    "港澳国旅",
                    BigDecimal.valueOf(529l),
                    BigDecimal.valueOf(409l),
                    BigDecimal.valueOf(122l),
                    BigDecimal.valueOf(0l),
                    BigDecimal.valueOf(0l),
                    "2019-06-28",
                    79,
                    0
            ));
            i+=2;
        }*/
        PaginationSupportDTO paginationSupportDTO=new PaginationSupportDTO();
        paginationSupportDTO.setTotalCount(16);
        paginationSupportDTO.setTotalPage(2);
        paginationSupportDTO.setItemList(onTimeSupplyOrderDTOList);
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(paginationSupportDTO);
        return response;
    }

    /**
     * 通知收款
     */
    @RequestMapping(value = "/notifyCollectionOfSupplyOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyCollectionOfSupplyOrder(@RequestBody NotifyCollectionOfSupplyOrderDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 通知付款
     */
    @RequestMapping(value = "/notifyPaymentOfSupplyOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyPaymentOfSupplyOrder(@RequestBody NotifyPaymentOfSupplyOrderDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 合并通知财务收款
     */
    @RequestMapping(value = "/notifyCollectionOfSupplyOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyCollectionOfSupplyOrderList(@RequestBody NotifyCollectionOfSupplyOrderListDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 合并通知财务付款
     */
    @RequestMapping(value = "/notifyPaymentOfSupplyOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyPaymentOfSupplyOrderList(@RequestBody NotifyPaymentOfSupplyOrderListDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 合并通知财务收款预览
     */
    @RequestMapping(value = "/notifyPaymentPreviewOfSupplyOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyPaymentPreviewOfSupplyOrderList(@RequestBody OrderIdListDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }
}
