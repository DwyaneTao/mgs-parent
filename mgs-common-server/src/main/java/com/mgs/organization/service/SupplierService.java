package com.mgs.organization.service;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.organization.remote.dto.QuerySupplierListDTO;
import com.mgs.organization.remote.dto.SupplierAddDTO;
import com.mgs.organization.remote.dto.SupplierListRequest;

/**
 * @author py
 * @date 2019/6/19 22:12
 **/
public interface SupplierService {
    /**
     * 新增供应商
     * @param
     * @return
     */
    Response addSupplier(SupplierAddDTO supplierAddDTO);

    /**
     * 修改供应商启用状态
     * @param
     * @return
     */
    Response modifySupplierStatus(SupplierAddDTO supplierAddDTO);
    /**
     * 修改供应商信息
     * @param
     * @return
     */
    Response modifySupplier(SupplierAddDTO supplierAddDTO);
    /**
     * 根据供应商Code,查询供应商详情
     */
    Response querySupplierDetail(SupplierAddDTO supplierAddDTO);

    /**
     * 查询供应商列表
     */
    PaginationSupportDTO<QuerySupplierListDTO> querySupplierList(SupplierListRequest request);
}

