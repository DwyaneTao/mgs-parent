package com.mgs.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/6/19 01:45
 * @Description: 酒店售价详情
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSalePriceDTO implements Serializable {

    /**
     * 酒店Id
     */
    private Integer hotelId;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 房型Id
     */
    private Integer roomId;

    /**
     * 房型名称
     */
    private String roomName;

    /**
     * 产品Id
     */
    private Integer productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品币种
     */
    private Integer currency;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 价格明细
     */
    private List<ProductSalePriceItemDTO> priceList;
}
