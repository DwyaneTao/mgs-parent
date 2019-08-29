package com.mgs.order.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "o_supply_request")
public class SupplyRequestPO  extends BasePO {
    /**
     * 供货单请求id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 供货单id
     */
    @Column(name = "supply_order_id")
    private Integer supplyOrderId;

    /**
     * 供货单类型：0预定单，1重发预订单，2修改单，3取消单
     */
    @Column(name = "supply_order_type")
    private Integer supplyOrderType;

    /**
     * 发送方式：0EBK，1api
     */
    @Column(name = "sending_type")
    private Integer sendingType;

    /**
     * 发送结果，0未发送，1成功，2失败
     */
    @Column(name = "sending_result")
    private Integer sendingResult;

    /**
     * 商家备注
     */
    @Column(name = "merchant_remark")
    private String merchantRemark;

    /**
     * 本次确认状态：0未处理 1已确认，2已拒绝
     */
    @Column(name = "this_confirmation_status")
    private Integer thisConfirmationStatus;

    /**
     * 本次确认号
     */
    @Column(name = "this_confirmation_code")
    private String thisConfirmationCode;

    /**
     * 本次确认人
     */
    @Column(name = "this_supplier_confirmer")
    private String thisSupplierConfirmer;

    /**
     * 本次退改费
     */
    @Column(name = "this_refund_fee")
    private BigDecimal thisRefundFee;

    /**
     * 本次拒绝原因：变价，满房，其他自定义
     */
    @Column(name = "this_refused_reason")
    private String thisRefusedReason;

    /**
     * 本次确认备注
     */
    @Column(name = "this_confirmation_remark")
    private String thisConfirmationRemark;
}