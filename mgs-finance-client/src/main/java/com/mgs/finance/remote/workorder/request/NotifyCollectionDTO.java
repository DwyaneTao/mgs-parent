package com.mgs.finance.remote.workorder.request;

import com.mgs.common.BaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class NotifyCollectionDTO extends BaseDTO implements Serializable{

    /**
     * 商家编码
     */
    private String companyCode;

    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 业务类型：0订单，1供货单，2分销商账单，3供应商账单
     */
    private Integer businessType;

    /**
     * 通知明细
     */
    private List<NotifyItemDTO> notifyItemDTOList;

    /**
     * 内容
     */
    private String content;

    /**
     * 支付方式
     */
    private Integer paymentType;

    /**
     * 付款方
     */
    private String payer;

    /**
     * 收款方
     */
    private String receiver;

    /**
     * 收款金额
     */
    private BigDecimal collectionAmt;

    /**
     * 币种
     */
    private Integer currency;

    /**
     * 备注
     */
    private String remark;

    /**
     * 凭证照片list
     */
    private List<WorkOrderAttchDTO> photoList;
}
