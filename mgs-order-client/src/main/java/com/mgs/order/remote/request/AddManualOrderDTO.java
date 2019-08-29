package com.mgs.order.remote.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class AddManualOrderDTO implements Serializable {

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 分销商编码
     */
    private String agentCode;

    /**
     * 分销商名称
     */
    private String agentName;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机
     */
    private String contactPhone;

    /**
     * 入住人
     */
    private List<String> guestList;

    /**
     * 酒店id
     */
    private Integer hotelId;

    /**
     * 酒店名称
     */
    private String hotelName;

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
     * 早餐数量
     */
    private Integer breakfastQty;

    /**
     * 采购类型：1自签协议房，2自签包房
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
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 采购金额
     */
    private BigDecimal supplyOrderAmt;

    /**
     * 售价币种
     */
    private Integer saleCurrency;

    /**
     * 底价币种
     */
    private Integer baseCurrency;

    /**
     * 商家编码
     */
    private String companyCode;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 渠道订单号
     */
    private String channelOrderCode;

    /**
     * 价格列表
     */
    private List<PriceRequestDTO> priceList;
}
