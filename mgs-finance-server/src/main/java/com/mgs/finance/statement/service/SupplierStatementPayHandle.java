package com.mgs.finance.statement.service;

import com.mgs.common.Response;
import com.mgs.finance.statement.dto.CancelStatementWorkOrderDTO;
import com.mgs.finance.statement.dto.ConfirmStatementWorkOrderDTO;

public interface SupplierStatementPayHandle {

    /**
     * 确认工单
     */
    Response confirmStatementWorkOrder(ConfirmStatementWorkOrderDTO request);

    /**
     * 取消工单
     */
    Response cancelStatementWorkOrder(CancelStatementWorkOrderDTO request);
}
