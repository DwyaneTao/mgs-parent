package com.mgs.dis.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 15:36
 * @Description:渠道分销商
 */
@Data
public class DisAgentDTO extends BaseDTO {

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
     * 客户编码
     */
    private String agentCode;

    /**
     * 客户名称
     */
    private String agentName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 有效性
     */
    private Integer active;
}
