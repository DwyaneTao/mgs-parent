package com.mgs.finance.lock.service;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.finance.remote.lock.request.FinanceLockOrderDTO;
import com.mgs.finance.remote.lock.request.FinanceLockSupplyOrderDTO;
import com.mgs.finance.remote.lock.request.QueryOrderFinanceLockListDTO;
import com.mgs.finance.remote.lock.request.QuerySupplyOrderFinanceLockListDTO;
import com.mgs.finance.remote.lock.response.OrderFinanceLockDTO;
import com.mgs.finance.remote.lock.response.SupplyOrderFinanceLockDTO;

public interface FinanceLockService {

    /**
     * 财务订单锁查询
     */
    PaginationSupportDTO<OrderFinanceLockDTO> queryOrderList(QueryOrderFinanceLockListDTO request);

    /**
     * 财务订单加锁
     */
    Response lockOrder(FinanceLockOrderDTO request);

    /**
     * 财务供货单锁查询
     */
    PaginationSupportDTO<SupplyOrderFinanceLockDTO> querySupplyOrderList(QuerySupplyOrderFinanceLockListDTO request);

    /**
     * 财务订单加锁
     */
    Response lockSupplyOrder(FinanceLockSupplyOrderDTO request);
}
