package com.mgs.dis.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 15:26
 * @Description: 渠道对应分销商
 */
@Data
@Table(name = "t_dis_channel_agent")
public class DisAgentPO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
