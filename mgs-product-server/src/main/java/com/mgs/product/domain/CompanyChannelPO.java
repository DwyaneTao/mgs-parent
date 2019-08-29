package com.mgs.product.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @Auther: Owen
 * @Date: 2019/6/18 22:52
 * @Description: 运营商渠道
 */
@Data
@Table(name = "t_pro_m_channel")
public class CompanyChannelPO extends BasePO {

    @Id
    @Column(name = "m_channel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String companyCode;

    private Integer channelId;

    private String channelCode;

    private Integer active;
}
