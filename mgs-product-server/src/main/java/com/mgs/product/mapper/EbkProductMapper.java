package com.mgs.product.mapper;

import com.mgs.common.MyMapper;
import com.mgs.product.domain.ProductPO;
import com.mgs.product.dto.ProductHotelDTO;
import com.mgs.product.dto.ProductTempDTO;
import com.mgs.product.dto.QueryProductRequestDTO;

import java.util.List;

/**
 * @author py
 * @date 2019/8/6 17:13
 **/
public interface EbkProductMapper extends MyMapper<ProductPO> {
    List<ProductHotelDTO> queryHotelList(QueryProductRequestDTO queryProductRequestDTO);
    List<ProductTempDTO> queryHotelProducts(QueryProductRequestDTO queryProductRequestDTO);
}
