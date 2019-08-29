package com.mgs.controller.finance.ontime;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.order.remote.request.NotifyCollectionOfOrderDTO;
import com.mgs.order.remote.request.NotifyCollectionOfOrderListDTO;
import com.mgs.order.remote.request.NotifyPaymentOfOrderDTO;
import com.mgs.order.remote.request.NotifyPaymentOfOrderListDTO;
import com.mgs.order.remote.request.OrderIdListDTO;
import com.mgs.order.remote.request.QueryOnTimeOrderListDTO;
import com.mgs.order.remote.response.OnTimeOrderDTO;
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
@RequestMapping(value = "/test/finance/agent")
public class TestOrderOnTimeController {

    /**
     * 单结订单查询
     */
    @RequestMapping(value = "/queryOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryOrderList(@RequestBody QueryOnTimeOrderListDTO request) {
        List<OnTimeOrderDTO> onTimeOrderDTOList=new ArrayList<>();
        /*for (int i=0;i<15;){
            onTimeOrderDTOList.add(new OnTimeOrderDTO(
                    i,
                    "H1805240000"+i,
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
            onTimeOrderDTOList.add(new OnTimeOrderDTO(
                    i+1,
                    "H1805240000"+(i+1),
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
        paginationSupportDTO.setItemList(onTimeOrderDTOList);
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(paginationSupportDTO);
        return response;
    }

    /**
     * 通知收款
     */
    @RequestMapping(value = "/notifyCollectionOfOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyCollectionOfOrder(@RequestBody NotifyCollectionOfOrderDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 通知付款
     */
    @RequestMapping(value = "/notifyPaymentOfOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyPaymentOfOrder(@RequestBody NotifyPaymentOfOrderDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 合并通知财务收款
     */
    @RequestMapping(value = "/notifyCollectionOfOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyCollectionOfOrderList(@RequestBody NotifyCollectionOfOrderListDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 合并通知财务付款
     */
    @RequestMapping(value = "/notifyPaymentOfOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyPaymentOfOrderList(@RequestBody NotifyPaymentOfOrderListDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 合并通知财务收款预览
     */
    @RequestMapping(value = "/notifyCollectionPreviewOfOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyCollectionPreviewOfOrderList(@RequestBody OrderIdListDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 合并通知财务付款预览
     */
    @RequestMapping(value = "/notifyPaymentPreviewOfOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response notifyPaymentPreviewOfOrderList(@RequestBody OrderIdListDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }
}
