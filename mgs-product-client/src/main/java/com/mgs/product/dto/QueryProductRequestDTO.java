package com.mgs.product.dto;

import com.mgs.common.BaseRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/4/24 14:47
 * @Description:
 */
@Data
public class QueryProductRequestDTO extends BaseRequest implements Serializable {

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 酒店Id
     */
    private Integer hotelId;

    /**
     * 酒店Id列表
     */
    private List<Integer> hotelIdList;

    /**
     * 是否含产品
     */
    private Integer hasProducts;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 销售状态（1销售中 0仓库中）
     */
    private String saleStatus;

    /**
     * 销售渠道（B2B,B2C,ctrip,qunar,feizhu,meituan,tcyl）
     */
    private String channelCode;

    /**
     * 产品Id
     */
    private Integer productId;
    /**
     * 房型Id
     */
    private  Integer roomId;
    /**
     * 运营商编码
     */
    private String companyCode;
    /**
     * 采购方式
     */
    private  Integer purchaseType;

    /**
     * 停售状态
     */
    private Integer offShelveProduct;
}
