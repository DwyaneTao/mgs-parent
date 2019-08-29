package com.mgs.product.mapper;

import com.mgs.common.MyMapper;
import com.mgs.product.domain.ProductPricePO;
import com.mgs.product.dto.ProductDayQuotationDTO;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/6/18 23:39
 * @Description: 产品价格
 */
public interface ProductPriceMapper extends MyMapper<ProductPricePO> {

    /**
     * 批量修改价格（merge）
     * @param list
     */
    void batchModifyBasePrice(List<ProductDayQuotationDTO> list);

    /**
     * 批量修改底价（add）
     * @param list
     */
    void batchModifyBasePriceAdd(List<ProductDayQuotationDTO> list);

    /**
     * 批量修改底价（subtract）
     * @param list
     */
    void batchModifyBasePriceSubtract(List<ProductDayQuotationDTO> list);

    /**
     * 批量修改底价（equals）
     * @param list
     */
    void batchModifyBasePriceEquals(List<ProductDayQuotationDTO> list);
}
