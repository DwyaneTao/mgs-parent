package com.mgs.order.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAttachmentDTO implements Serializable{

    /**
     * 附件ID
     */
    private Integer orderAttachmentId;

    /**
     * 附件名称
     */
    private String name;

    /**
     * url地址
     */
    private String url;
}
