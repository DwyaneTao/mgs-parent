package com.mgs.dis.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 15:36
 * @Description:渠道配置
 */
@Data
public class DisConfigDTO extends BaseDTO {

    private Integer id;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 运营商编码
     */
    private String companyCode;

    /**
     * 运营商名称
     */
    private String companyName;

    /**
     * 配置名称
     */
    private String fieldName;

    /**
     * 配置值
     */
    private String fieldValue;

    /**
     * 备注
     */
    private String remark;
}
