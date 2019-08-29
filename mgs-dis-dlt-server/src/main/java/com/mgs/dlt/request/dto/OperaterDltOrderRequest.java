package com.mgs.dlt.request.dto;

import com.mgs.dlt.request.base.BaseRequest;
import com.mgs.dlt.request.base.Requestor;
import lombok.Data;

import java.math.BigDecimal;

/**
 *   2018/4/8.
 */
@Data
public class OperaterDltOrderRequest extends BaseRequest {

    private Requestor requestor;
    private Integer supplierID;
    private String dltOrderId;
    private Integer operaterType;
    private Integer confirmType;
    private Integer refuseType;
    private String refuseRemark;
    private BigDecimal refundAmount;
    private String bookingNo;
    private String remark;
    /** 不序列化 */
    private transient String channelCode;

}
