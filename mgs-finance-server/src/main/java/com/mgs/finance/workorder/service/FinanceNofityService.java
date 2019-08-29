package com.mgs.finance.workorder.service;

import com.mgs.common.Response;
import com.mgs.finance.remote.workorder.request.NotifyCollectionDTO;
import com.mgs.finance.remote.workorder.request.NotifyPaymentDTO;
import com.mgs.finance.remote.workorder.request.BusinessCodeDTO;
import com.mgs.finance.remote.workorder.response.NotificationLogDTO;

import java.util.List;

public interface FinanceNofityService {

    /**
     * 通知收款
     */
    Response notifyCollection(NotifyCollectionDTO request);

    /**
     * 通知付款
     */
    Response notifyPayment(NotifyPaymentDTO request);

    /**
     * 通知记录查询
     */
    List<NotificationLogDTO> financeNotificationLogList(BusinessCodeDTO request);
}
