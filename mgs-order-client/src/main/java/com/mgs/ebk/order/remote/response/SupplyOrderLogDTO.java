package com.mgs.ebk.order.remote.response;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class SupplyOrderLogDTO implements Serializable {


    /**
     * 内容
     */
    private String content;


    /**
     * 操作时间
     */
    private String createdDt;


    /**
     * 创建人
     */
    private String CreatedBy;
}
