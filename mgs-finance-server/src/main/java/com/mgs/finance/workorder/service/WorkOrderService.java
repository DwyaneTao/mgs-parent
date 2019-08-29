package com.mgs.finance.workorder.service;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.finance.remote.workorder.request.ConfirmWorkOrderDTO;
import com.mgs.finance.remote.workorder.request.QueryWorkOrderListDTO;
import com.mgs.finance.remote.workorder.request.WorkOrderIdDTO;
import com.mgs.finance.remote.workorder.response.WorkOrderDTO;
import com.mgs.finance.remote.workorder.response.WorkOrderListResponseDTO;

public interface WorkOrderService {

    /**
     * 财务工单查询
     */
    PaginationSupportDTO<WorkOrderListResponseDTO> queryWorkOrderList(QueryWorkOrderListDTO request);

    /**
     * 工单详情
     */
    WorkOrderDTO queryWorkOrderDetail(WorkOrderIdDTO request);

    /**
     * 确认工单
     */
    Response confirmWorkOrder(ConfirmWorkOrderDTO request);

    /**
     * 删除工单
     */
    Response deleteWorkOrder(WorkOrderIdDTO request);
}
