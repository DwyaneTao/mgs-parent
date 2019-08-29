package com.mgs.controller.finance.workorder;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.finance.remote.workorder.request.BusinessCodeDTO;
import com.mgs.finance.remote.workorder.request.ConfirmWorkOrderDTO;
import com.mgs.finance.remote.workorder.request.QueryWorkOrderListDTO;
import com.mgs.finance.remote.workorder.request.WorkOrderIdDTO;
import com.mgs.finance.remote.workorder.response.NotificationLogDTO;
import com.mgs.finance.remote.workorder.response.WorkOrderDTO;
import com.mgs.finance.remote.workorder.response.WorkOrderListResponseDTO;
import com.mgs.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/test/finance/workorder")
public class TestWorkOrderController {

    /**
     * 财务工单查询
     */
    @RequestMapping(value = "/queryWorkOrderList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryWorkOrderList(@RequestBody QueryWorkOrderListDTO request) {
        /*List<WorkOrderListResponseDTO> workOrderListResponseDTOList=new ArrayList<>();
        for (int i=0;i<15;){
            workOrderListResponseDTOList.add(new WorkOrderListResponseDTO(
                    i,
                    "F1808270000"+i,
                    0,
                    "李雷",
                    "订单款",
                    Arrays.asList("H18082700001"),
                    "深圳环球旅行",
                    BigDecimal.valueOf(50l),
                    null,
                    DateUtil.stringToDate("2019-06-26"),
                    0
            ));
            workOrderListResponseDTOList.add(new WorkOrderListResponseDTO(
                    i+1,
                    "F1808270000"+i,
                    1,
                    "李雷",
                    "账单款",
                    Arrays.asList("B18082700001"),
                    "港澳国旅",
                    BigDecimal.valueOf(1200l),
                    null,
                    DateUtil.stringToDate("2019-06-27"),
                    1
            ));
            i+=2;
        }
        PaginationSupportDTO paginationSupportDTO=new PaginationSupportDTO();
        paginationSupportDTO.setTotalCount(16);
        paginationSupportDTO.setTotalPage(2);
        paginationSupportDTO.setItemList(workOrderListResponseDTOList);*/
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        //response.setModel(paginationSupportDTO);
        return response;
    }

    /**
     * 工单详情
     */
    @RequestMapping(value = "/queryWorkOrderDetail",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response queryWorkOrderDetail(@RequestBody WorkOrderIdDTO request) {
       /* WorkOrderDTO workOrderDTO=new WorkOrderDTO(
                1,
                0,
                0,
                0,
                "工商银行（6565613312312388688）",
                "工商银行（6565613312312381245）",
                BigDecimal.valueOf(1000),
                "",
                Arrays.asList()

        );*/
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
       // response.setModel(workOrderDTO);
        return response;
    }

    /**
     * 确认工单
     */
    @RequestMapping(value = "/confirmWorkOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response confirmWorkOrder(@RequestBody ConfirmWorkOrderDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 删除工单
     */
    @RequestMapping(value = "/deleteWorkOrder",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response deleteWorkOrder(@RequestBody WorkOrderIdDTO request) {
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 通知记录查询
     */
    @RequestMapping(value = "/financeNotificationLogList",produces = { "application/json;charset=UTF-8" })
    @ResponseBody
    public Response financeNotificationLogList(@RequestBody BusinessCodeDTO request) {
        List<NotificationLogDTO> notificationLogDTOList=new ArrayList<>();
        for (int i=0;i<5;){
            notificationLogDTOList.add(new NotificationLogDTO(
                    "F1808270000"+i,
                    0,
                    BigDecimal.valueOf(288l),
                    null,
                    0,
                    new Date(),
                    "李雷"
            ));
            notificationLogDTOList.add(new NotificationLogDTO(
                    "F1808270000"+(i+1),
                    1,
                    null,
                    BigDecimal.valueOf(288l),
                    0,
                    new Date(),
                    "李小红"
            ));
            i+=2;
        }
        Response response=new Response(ResultCodeEnum.SUCCESS.code);
        response.setModel(notificationLogDTOList);
        return response;
    }
}
