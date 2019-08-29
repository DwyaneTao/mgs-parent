package com.mgs.product.mapper;

import com.mgs.common.MyMapper;
import com.mgs.product.domain.ProductPricePO;
import com.mgs.product.domain.ProductQuotaPO;
import com.mgs.product.dto.ProductDayQuotationDTO;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/6/18 23:39
 * @Description: 产品配额
 */
public interface ProductQuotaMapper extends MyMapper<ProductQuotaPO> {

    /**
     * 批量修改房态配额（add）
     * @param productDayQuotationDTOList
     */
    void batchModifyQuotaAdd(List<ProductDayQuotationDTO> productDayQuotationDTOList);

    /**
     * 批量修改房态配额（subtract）
     * @param productDayQuotationDTOList
     */
    void batchModifyQuotaSubtract(List<ProductDayQuotationDTO> productDayQuotationDTOList);

    /**
     * 批量修改房态配额（equals）
     * @param productDayQuotationDTOList
     */
    void batchModifyQuotaEquals(List<ProductDayQuotationDTO> productDayQuotationDTOList);

    /**
     * 总配额减少时，可能改为负数
     * @param list
     */
    void updateQuotaLessThanZero(List<Integer> list);

    /**
     * 剩余配额减少时，可能改为负数
     * @param list
     */
    void updateRemainingQuotaLessThanZero(List<Integer> list);
}
