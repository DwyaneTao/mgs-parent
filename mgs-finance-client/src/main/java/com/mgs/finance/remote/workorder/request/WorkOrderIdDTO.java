package com.mgs.finance.remote.workorder.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class WorkOrderIdDTO implements Serializable{

    /**
     * 工单id
     */
    private Integer workOrderId;

    /**
     * 操作人
     */
    private String operator;
}
