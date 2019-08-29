package com.mgs.dis.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @Auther: Owen
 * @Date: 2019/7/26 20:06
 * @Description: 酒店映射
 */
@Data
@Table(name = "t_dis_mapping_hotel")
public class DisHotelMappingPO extends BasePO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 酒店Id
     */
    private Integer hotelId;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 分销端酒店Id
     */
    private String disHotelId;

    /**
     * 分销商（OTA，分销端）
     */
    private String distributor;

    /**
     * 运营商编码
     */
    private String companyCode;
}
