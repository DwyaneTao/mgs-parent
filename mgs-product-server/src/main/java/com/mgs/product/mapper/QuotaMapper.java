package com.mgs.product.mapper;


import com.mgs.common.MyMapper;
import com.mgs.product.domain.QuotaPO;
import feign.Param;

import java.util.List;
import java.util.Map;

public interface QuotaMapper extends MyMapper<QuotaPO> {

      List<QuotaPO> queryQuota(Map<String,String> request);

      String   queryQuotaAccountId(@Param("productId") Integer productId);
}
