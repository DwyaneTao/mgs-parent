package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyProductDetailDTO implements Serializable{

    /**
     * 供货单产品id
     */
    private Integer supplyProductId;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 结算币种
     */
    private Integer settlementCurrency;

    /**
     * 早餐数量
     */
    private Integer breakfastQty;

    /**
     * 采购类型：0自签协议房，1自签包房
     */
    private Integer purchaseType;

    /**
     * 入住日期
     */
    private String startDate;

    /**
     * 离店日期
     */
    private String endDate;

    /**
     * 间数
     */
    private Integer roomQty;

    /**
     * 入住人列表
     */
    private List<SupplyGuestDTO> guestList;

    /**
     * 底价
     */
    private BigDecimal basePrice;

    /**
     * 底价币种
     */
    private Integer baseCurrency;

    /**
     * 折合金额
     */
    private BigDecimal exchangeBasePrice;

    /**
     * 价格列表
     */
    private List<SupplyProductPriceDTO> priceList;
}
