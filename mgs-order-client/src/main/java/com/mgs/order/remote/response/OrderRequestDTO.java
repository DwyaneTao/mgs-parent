package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO implements Serializable{

    /**
     * 请求id
     */
    private Integer orderRequestId;

    /**
     * 请求类型：1取消，2修改
     */
    private Integer requestType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 处理结果：0未处理，1同意处理，2拒绝处理
     */
    private Integer handledResult;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private String createdDt;

    /**
     * 修改人
     */
    private String modifiedBy;

    /**
     * 修改时间
     */
    private String modifiedDt;

    /**
     * 分销商名称
     */
    private String agentName;
}
