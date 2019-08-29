package com.mgs.finance.lock.mapper;

import com.mgs.finance.remote.lock.request.FinanceLockOrderDTO;
import com.mgs.finance.remote.lock.request.FinanceLockSupplyOrderDTO;
import com.mgs.finance.remote.lock.request.QueryOrderFinanceLockListDTO;
import com.mgs.finance.remote.lock.request.QuerySupplyOrderFinanceLockListDTO;
import com.mgs.finance.remote.lock.response.OrderFinanceLockDTO;
import com.mgs.finance.remote.lock.response.SupplyOrderFinanceLockDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FinanceLockMapper {

    /**
     * 财务订单锁查询
     */
    List<OrderFinanceLockDTO> queryOrderList(QueryOrderFinanceLockListDTO request);

    /**
     * 财务订单加锁
     */
    Integer lockOrder(FinanceLockOrderDTO request);

    /**
     * 财务供货单锁查询
     */
    List<SupplyOrderFinanceLockDTO> querySupplyOrderList(QuerySupplyOrderFinanceLockListDTO request);

    /**
     * 财务订单加锁
     */
    Integer lockSupplyOrder(FinanceLockSupplyOrderDTO request);

    /**
     * 检查订单是否结算
     * @return
     */
    Integer checkOrderCanLock(@Param("orderId") Integer orderId);

    /**
     * 检查供货单是否结算
     * @return
     */
    Integer checkSupplyOrderCanLock(@Param("supplyOrderId") Integer supplyOrderId);
}
