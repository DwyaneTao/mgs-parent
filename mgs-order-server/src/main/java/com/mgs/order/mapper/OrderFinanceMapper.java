package com.mgs.order.mapper;

import com.mgs.common.MyMapper;
import com.mgs.order.domain.OrderFinancePO;
import com.mgs.order.dto.OrderAmtDTO;
import com.mgs.order.remote.request.OrderIdListDTO;

import java.util.List;

public interface OrderFinanceMapper extends MyMapper<OrderFinancePO> {

    /**
     * 查询订单金额
     */
    List<OrderAmtDTO> queryOrderAmt(OrderIdListDTO request);
}