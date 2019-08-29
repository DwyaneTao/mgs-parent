package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyOrderDTO implements Serializable{

    /**
     * 供货单id
     */
    private Integer supplyOrderId;

    /**
     * 供货单编码
     */
    private String supplyOrderCode;

    /**
     * 发单状态：0未发单，1已发预订单，2已重发预订单，3已发修改单，4已发取消单
     */
    private Integer sendingStatus;

    /**
     * 确认状态：0未确认，1确认，2已取消
     */
    private Integer confirmationStatus;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 供货单金额
     */
    private BigDecimal supplyOrderAmt;

    /**
     * 底价
     */
    private BigDecimal basePrice;

    /**
     * 退改费
     */
    private BigDecimal refundFee;

    /**
     * 底价币种
     */
    private Integer baseCurrency;

    /**
     * 底价列表
     */
    private List<PriceResponseDTO> basePriceList;

    /**
     * 结算方式：0月结 1半月结 2周结 3单结 4日结
     */
    private Integer settlementType;

    /**
     * 结算状态：0未结算，1已结算
     */
    private Integer settlementStatus;

    /**
     * 产品列表
     */
    private List<SupplyProductDTO> productList;

    /**
     * 已付金额
     */
    private BigDecimal paidAmt;

    /**
     * 未付金额
     */
    private BigDecimal unpaidAmt;

    /**
     * 收款未确认金额
     */
    private BigDecimal unconfirmedReceivedAmt;

    /**
     * 付款未确认金额
     */
    private BigDecimal unconfirmedPaidAmt;

    /**
     * 供应商订单号
     */
    private String supplierOrderCode;

    /**
     * 财务锁单
     */
    private Integer financeLockStatus;
}
