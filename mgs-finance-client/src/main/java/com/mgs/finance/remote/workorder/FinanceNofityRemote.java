package com.mgs.finance.remote.workorder;

import com.mgs.common.Response;
import com.mgs.finance.remote.workorder.request.NotifyCollectionDTO;
import com.mgs.finance.remote.workorder.request.NotifyPaymentDTO;
import com.mgs.finance.remote.workorder.request.BusinessCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mgs-finance-server")
public interface FinanceNofityRemote {

    /**
     * 通知收款
     */
    @PostMapping("/finance/notify/notifyCollection")
    Response notifyCollection(@RequestBody NotifyCollectionDTO request);

    /**
     * 通知付款
     */
    @PostMapping("/finance/notify/notifyPayment")
    Response notifyPayment(@RequestBody NotifyPaymentDTO request);

    /**
     * 通知记录查询
     */
    @PostMapping("/finance/notify/financeNotificationLogList")
    Response financeNotificationLogList(@RequestBody BusinessCodeDTO request);
}
