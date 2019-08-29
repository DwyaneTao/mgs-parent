package com.mgs.product.mapper;

import com.mgs.common.MyMapper;
import com.mgs.product.domain.ProductDayIncreasePO;
import com.mgs.product.domain.ProductIncreasePO;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/6/18 23:39
 * @Description: 产品每日加幅
 */
public interface ProductDayIncreaseMapper extends MyMapper<ProductDayIncreasePO> {

    int mergeProductDayIncrease(List<ProductDayIncreasePO> list);
}
