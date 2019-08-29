package com.mgs.product.mapper;

import com.mgs.common.MyMapper;
import com.mgs.product.domain.DebitedQuotaPO;

import java.util.List;
import java.util.Map;

public interface DebitedQuotaMapper extends MyMapper<DebitedQuotaPO> {

   Integer insertDebitedQuota(List debitedQuota);

   List<DebitedQuotaPO>  queryDebitedQuota(Map<String,Integer> map);
}