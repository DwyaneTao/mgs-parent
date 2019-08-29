package com.mgs.order.mapper;

import com.mgs.common.MyMapper;
import com.mgs.order.domain.SupplierCompanyPO;

public interface SupplierCompanyMapper  extends MyMapper<SupplierCompanyPO> {

    SupplierCompanyPO querySupplierCompany(SupplierCompanyPO supplierCompanyPO);
}
