package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplyAttachmentDTO implements Serializable{

    /**
     * 附件ID
     */
    private Integer supplyAttachmentId;

    /**
     * 附件名称
     */
    private String name;

    /**
     * url地址
     */
    private String url;
}
