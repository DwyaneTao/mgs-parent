package com.mgs.order.dto;

import com.mgs.order.domain.GuestPO;
import com.mgs.order.domain.OrderFinancePO;
import com.mgs.order.domain.OrderPO;
import com.mgs.order.domain.OrderProductPricePO;
import lombok.Data;

import java.util.List;

@Data
public class AssemblyOrderDTO {

    /**
     * 订单信息
     */
    private OrderPO order;

    /**
     * 订单财务信息
     */
    private OrderFinancePO orderFinance;

    /**
     * 价格明细
     */
    private List<OrderProductPricePO> orderProductPriceList;

    /**
     * 入住人
     */
    private List<GuestPO> guestList;

    /**
     * 供货单
     */
    private List<AssemblySupplyOrderDTO> supplyOrderList;
}
