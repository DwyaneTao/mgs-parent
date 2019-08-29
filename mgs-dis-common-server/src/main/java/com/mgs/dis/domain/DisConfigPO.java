package com.mgs.dis.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @Auther: Owen
 * @Date: 2019/7/29 15:29
 * @Description: 渠道配置
 */
@Data
@Table(name = "t_dis_channel_config")
public class DisConfigPO extends BasePO {

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
