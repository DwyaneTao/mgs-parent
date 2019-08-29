package com.mgs.order.remote.request;

import com.mgs.common.BaseDTO;
import lombok.Data;

@Data
public class NotifyAttchDTO extends BaseDTO {

    private Integer id;

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