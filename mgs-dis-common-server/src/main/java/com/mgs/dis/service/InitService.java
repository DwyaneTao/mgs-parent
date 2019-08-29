package com.mgs.dis.service;

import com.mgs.dis.dto.ProductSaleIncreaseDTO;
import com.mgs.dis.dto.ProductSaleStatusDTO;
import com.mgs.product.dto.ProductDTO;
import com.mgs.product.dto.ProductDayQuotationDTO;

import java.util.List;

/**
 * redis初始化
 */
public interface InitService {

    /**
     * 初始化条款
     * @return
     */
    int initRestrict(ProductDTO productDTO);

    /**
     * 初始化底价和房态
     * @return
     */
    int initBasePriceAndRoomStatus(List<ProductDayQuotationDTO> productDayQuotationDTOList);

    /**
     * 初始化售价
     * @return
     */
    int initSalePrice(List<ProductSaleIncreaseDTO> productSaleIncreaseList);

    /**
     * 初始化上下架
     * @return
     */
    int initSaleStatus(List<ProductSaleStatusDTO> productSaleStatusList);

    /**
     * 初始化产品商家关系表
     * @return
     */
    int initProductCompanyRelation();
}
