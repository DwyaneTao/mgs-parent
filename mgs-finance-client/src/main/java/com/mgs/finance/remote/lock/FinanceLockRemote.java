package com.mgs.finance.remote.lock;

import com.mgs.common.Response;
import com.mgs.finance.remote.lock.request.FinanceLockOrderDTO;
import com.mgs.finance.remote.lock.request.FinanceLockSupplyOrderDTO;
import com.mgs.finance.remote.lock.request.QueryOrderFinanceLockListDTO;
import com.mgs.finance.remote.lock.request.QuerySupplyOrderFinanceLockListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mgs-finance-server")
public interface FinanceLockRemote {

    /**
     * 财务订单锁查询
     */
    @PostMapping("/finance/lock/queryOrderList")
    Response queryOrderList(@RequestBody QueryOrderFinanceLockListDTO request);

    /**
     * 财务订单加锁
     */
    @PostMapping("/finance/lock/lockOrder")
    Response lockOrder(@RequestBody FinanceLockOrderDTO request);

    /**
     * 财务供货单锁查询
     */
    @PostMapping("/finance/lock/querySupplyOrderList")
    Response querySupplyOrderList(@RequestBody QuerySupplyOrderFinanceLockListDTO request);

    /**
     * 财务订单加锁
     */
    @PostMapping("/finance/lock/lockSupplyOrder")
    Response lockSupplyOrder(@RequestBody FinanceLockSupplyOrderDTO request);
}
