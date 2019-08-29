package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLogDTO implements Serializable{

    /**
     * 内容
     */
    private String content;

    /**
     * 操作对象
     */
    private String target;

    /**
     * 操作人
     */
    private String createdBy;

    /**
     * 操作时间
     */
    private String createdDt;
}
