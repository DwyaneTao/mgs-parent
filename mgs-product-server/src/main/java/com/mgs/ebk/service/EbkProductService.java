package com.mgs.ebk.service;

import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.product.dto.BatchQuotationDTO;
import com.mgs.product.dto.ProductDTO;
import com.mgs.product.dto.ProductDayQuotationDTO;
import com.mgs.product.dto.ProductLogDTO;
import com.mgs.product.dto.ProductOrderQueryDTO;
import com.mgs.product.dto.ProductOrderQueryRequestDTO;
import com.mgs.product.dto.QueryProductRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author py
 * @date 2019/8/5 17:29
 **/

public interface EbkProductService {
    //由供应商code查询运营商code
    public  String getCompanyCode(String supplierCode);

    /**
     * 查询酒店列表
     * @param queryProductRequestDTO
     * @return
     */
    Response queryHotelList(QueryProductRequestDTO queryProductRequestDTO);

    /**
     * 查询酒店产品详情
     * @param queryProductRequestDTO
     * @return
     */
    Response queryHotelProducts(QueryProductRequestDTO queryProductRequestDTO);


}
