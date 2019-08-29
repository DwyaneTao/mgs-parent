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
public class SupplyResultDTO implements Serializable{

    /**
     * 供货单id
     */
    private Integer supplyOrderId;

    /**
     * 确认状态：0未确认，1确认，2已取消
     */
    private Integer confirmationStatus;

    /**
     * 供应商确认人
     */
    private String supplierConfirmer;

    /**
     * 确认号
     */
    private String confirmationCode;

    /**
     * 拒绝原因
     */
    private String refusedReason;

    /**
     * 备注
     */
    private String remark;

    /**
     * 退改费
     */
    private BigDecimal refundFee;

    /**
     * 操作人
     */
    private String operatedBy;

    /**
     * 操作时间
     */
    private String operatedTime;

    /**
     * 附件列表
     */
    private List<SupplyAttachmentDTO> supplyAttachmentList;
}
