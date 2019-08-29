package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRemarkDTO implements Serializable{

    /**
     * 备注
     */
    private String remark;

    /**
     * 备注对象
     */
    private String receiver;

    /**
     * 备注人
     */
    private String createdBy;

    /**
     * 备注时间
     */
    private String createdDt;
}
