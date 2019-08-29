package com.mgs.order.remote.response;

import com.mgs.common.BaseDTO;
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
public class OrderDTO extends BaseDTO implements Serializable{

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 订单编码
     */
    private String orderCode;

    /**
     * 订单确认状态：0待确认、1已确认、2已取消
     */
    private Integer confirmationStatus;

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
     * 产品名称
     */
    private String productName;

    /**
     * 早餐数
     */
    private Integer breakfastQty;

    /**
     * 入住日期
     */
    private String startDate;

    /**
     * 离店日期
     */
    private String endDate;

    /**
     * 晚数
     */
    private Integer nightQty;

    /**
     * 间数
     */
    private Integer roomQty;

    /**
     * 入住人
     */
    private String guest;

    /**
     * 特殊要求
     */
    private String specialRequest;

    /**
     * 确认号
     */
    private String confirmationCode;

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
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactTel;

    /**
     * 渠道订单号
     */
    private String channelOrderCode;

    /**
     * 订单金额
     */
    private BigDecimal orderAmt;

    /**
     * 售价
     */
    private BigDecimal salePrice;

    /**
     * 退改费
     */
    private BigDecimal refundFee;

    /**
     * 售价币种
     */
    private Integer saleCurrency;

    /**
     * 结算方式：0月结 1半月结 2周结 3单结 4日结
     */
    private Integer settlementType;

    /**
     * 结算状态：0未结算，1已结算
     */
    private Integer settlementStatus;

    /**
     * 供货单总金额
     */
    private BigDecimal supplyOrderTotalAmt;

    /**
     * 利润
     */
    private BigDecimal profit;

    /**
     * 售价明细
     */
    private List<PriceResponseDTO> salePriceList;

    /**
     * 供货单列表
     */
    private List<SupplyOrderDTO> supplyOrderList;

    /**
     * 订单附件
     */
    private List<OrderAttachmentDTO> orderAttachmentList;

    /**
     * 是否手工单
     */
    private Integer isManualOrder;

    /**
     * 是否代下单
     */
    private Integer isSubstituted;

    /**
     * 已收金额
     */
    private BigDecimal receivedAmt;

    /**
     * 未收金额
     */
    private BigDecimal unreceivedAmt;

    /**
     * 收款未确认
     */
    private BigDecimal unconfirmedReceivedAmt;

    /**
     * 付款未确认
     */
    private BigDecimal unconfirmedPaidAmt;

    /**
     * 订单归属人
     */
    private String orderOwnerName;

    /**
     * 修改状态：0无，1取消中，2修改中
     */
    private Integer modificationStatus;

    /**
     * 订单确认人
     */
    private String confirmer;

    /**
     * 订单确认时间
     */
    private String confirmTime;

    /**
     *客人特殊要求
     */
    private Integer isShownOnSupplyOrder;

    /**
     * 财务锁单
     */
    private Integer financeLockStatus;

    /**
     * 是否我的订单 0:不是 1:是的
     */
    private Integer isMyOrder;

    /**
     * 是否即时确认： 0是，1否
     */
    private Integer instantConfimationStatus;

}
