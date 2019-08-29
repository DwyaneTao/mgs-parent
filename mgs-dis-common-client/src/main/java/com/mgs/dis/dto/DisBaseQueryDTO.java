package com.mgs.dis.dto;

import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 15:45
 * @Description:分销基础查询条件
 */
@Data
public class DisBaseQueryDTO {

    /**
     * 分销商（OTA，分销端）
     */
    private String distributor;

    /**
     * 运营商编码
     */
    private String companyCode;

    /**
     * 渠道编码
     */
    private String channelCode;
}
