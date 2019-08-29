package com.mgs.product.dto;

import com.mgs.common.BaseRequest;
import lombok.Data;

import java.util.Date;

/**
 * @author py
 * @date 2019/7/17 10:26
 * 订单所需产品参数请求列表
 **/
@Data
public class ProductOrderQueryRequestDTO extends BaseRequest {
    /**
     * 酒店Id
     */
    private Integer hotelId;
    /**
     * 房型Id
     */
    private  Integer  roomId;
    /**
     * 供应商编码
     */
    private  String supplierCode;
    /**
     * 开始时间
     */
    private String startDate;
    /**
     * 结束时间
     */
    private  String endDate;
    /**
     * 间数
     */
    private  Integer roomQty;
    /**
     * 运营商编码
     */
    private  String companyCode;

}
