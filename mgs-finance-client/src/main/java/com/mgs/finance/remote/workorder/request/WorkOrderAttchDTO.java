package com.mgs.finance.remote.workorder.request;

import com.mgs.common.BaseDTO;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class WorkOrderAttchDTO extends BaseDTO {

    private Integer id;

    /**
     * 工单id
     */
    private Integer workOrderId;

    /**
     * 附件名称
     */
    private String name;

    /**
     * url地址
     */
    private String url;

    /**
     * 实际地址
     */
    private String realpath;
}