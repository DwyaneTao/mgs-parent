package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class AddProductDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 房型id
     */
    private Integer roomId;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 底价币种
     */
    private Integer baseCurrency;

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
     * 房间数
     */
    private Integer roomQty;

    /**
     * 总金额
     */
    private BigDecimal basePriceTotalAmt;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 价格列表
     */
    private List<PriceRequestDTO> priceList;

    /**
     * 订单归属人
     */
    private String orderOwnerName;
}
