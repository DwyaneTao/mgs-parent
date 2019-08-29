package com.mgs.finance.workorder.mapper;

import com.mgs.common.MyMapper;
import com.mgs.finance.remote.workorder.response.NotificationLogDTO;
import com.mgs.finance.remote.workorder.request.BusinessCodeDTO;
import com.mgs.finance.remote.workorder.request.QueryWorkOrderListDTO;
import com.mgs.finance.remote.workorder.response.WorkOrderListResponseDTO;
import com.mgs.finance.workorder.domain.WorkOrderPO;

import java.util.List;

public interface WorkOrderMapper extends MyMapper<WorkOrderPO> {

    /**
     * 财务工单查询
     */
    List<WorkOrderListResponseDTO> queryWorkOrderList(QueryWorkOrderListDTO request);

    /**
     * 通知记录查询
     */
    List<NotificationLogDTO> financeNotificationLogList(BusinessCodeDTO request);
}