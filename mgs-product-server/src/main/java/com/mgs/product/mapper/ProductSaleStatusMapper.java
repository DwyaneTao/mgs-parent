package com.mgs.product.mapper;

import com.mgs.common.MyMapper;
import com.mgs.product.domain.ProductPricePO;
import com.mgs.product.domain.ProductSaleStatusPO;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/6/18 23:39
 * @Description: 产品售卖情况
 */
public interface ProductSaleStatusMapper extends MyMapper<ProductSaleStatusPO> {

    int batchModifyProductSaleStatus(List<ProductSaleStatusPO> list);
}
