package com.mgs.organization.remote;

import com.mgs.common.Response;
import com.mgs.organization.remote.dto.SupplierAddDTO;
import com.mgs.organization.remote.dto.SupplierListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author py
 * @date 2019/7/2 15:18
 **/
@FeignClient(value = "mgs-common-server")
public interface SupplierRemote {
    /**
     * 添加供应商消息
     * @param request
     * @return
     */
    @PostMapping("/supplier/addSupplier")
    Response addSupplier(@RequestBody SupplierAddDTO request);
    /**
     * 修改供应商启用状态
     */
    @PostMapping("/supplier/modifySupplierStatus")
    Response modifySupplierStatus(@RequestBody SupplierAddDTO request);
    /**
     * 修改供应商信息
     */
    @PostMapping("/supplier/modifySupplier")
    Response modifySupplier(@RequestBody SupplierAddDTO request);
    /**
     * 供应商详情
     */
    @PostMapping("/supplier/querySupplierDetail")
    Response querySupplierDetail(@RequestBody SupplierAddDTO request);
    /**
     * 查询供应商列表
     */
    @PostMapping("/supplier/querySupplierList")
    Response querySupplierList(@RequestBody SupplierListRequest request);

}
