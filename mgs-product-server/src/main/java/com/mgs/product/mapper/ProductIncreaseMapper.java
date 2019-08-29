package com.mgs.product.mapper;

import com.mgs.common.MyMapper;
import com.mgs.product.domain.ProductIncreasePO;
import com.mgs.product.domain.ProductPricePO;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/6/18 23:39
 * @Description: 产品加幅
 */
public interface ProductIncreaseMapper extends MyMapper<ProductIncreasePO> {

    /**
     * 查询一天的加幅数据
     * 按创建日期倒叙取第一条数据
     * @param paramMap
     * @return
     */
    List<ProductIncreasePO> queryOneDayIncrease(Map<String,Object> paramMap);
}
