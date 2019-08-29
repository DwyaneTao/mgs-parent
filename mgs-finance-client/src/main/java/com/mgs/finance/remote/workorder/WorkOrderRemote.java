package com.mgs.finance.remote.workorder;

import com.mgs.common.Response;
import com.mgs.finance.remote.workorder.request.ConfirmWorkOrderDTO;
import com.mgs.finance.remote.workorder.request.QueryWorkOrderListDTO;
import com.mgs.finance.remote.workorder.request.WorkOrderIdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mgs-finance-server")
public interface WorkOrderRemote {

    /**
     * 财务工单查询
     */
    @PostMapping("/finance/workorder/queryWorkOrderList")
    Response queryWorkOrderList(@RequestBody QueryWorkOrderListDTO request);

    /**
     * 工单详情
     */
    @PostMapping("/finance/workorder/queryWorkOrderDetail")
    Response queryWorkOrderDetail(@RequestBody WorkOrderIdDTO request);

    /**
     * 确认工单
     */
    @PostMapping("/finance/workorder/confirmWorkOrder")
    Response confirmWorkOrder(@RequestBody ConfirmWorkOrderDTO request);

    /**
     * 删除工单
     */
    @PostMapping("/finance/workorder/deleteWorkOrder")
    Response deleteWorkOrder(@RequestBody WorkOrderIdDTO request);
}
