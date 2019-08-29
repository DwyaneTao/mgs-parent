package com.mgs.product.mapper;

import com.mgs.common.MyMapper;
import com.mgs.product.domain.ProductPO;
import com.mgs.product.dto.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Owens
 * @Date: 2019/4/24 11:24
 * @Description:产品
 */
@Component(value = "productMapper")
public interface ProductMapper extends MyMapper<ProductPO> {

    List<ProductHotelDTO> queryHotelList(QueryProductRequestDTO queryProductRequestDTO);

    List<ProductTempDTO> queryHotelProducts(QueryProductRequestDTO queryProductRequestDTO);

    List<Integer> querySaleHotelIds(QueryProductRequestDTO queryProductRequestDTO);

    List<ProductTempDTO> querySaleProducts(QueryProductRequestDTO queryProductRequestDTO);

    ProductDTO queryProduct(Integer productId);
    /**
     * 按照日期查询产品低价
     */
    List<TotalAmtDTO> queryTotalAmt(ProductOrderQueryRequestDTO productOrderQueryRequestDTO);

    /**
     * 查询产品操作日志
     * @param requestMap
     * @return
     */
    List<ProductLogDTO> queryProductLogList(Map<String, String> requestMap);

    /**
     * 查询产品售卖日志
     * @param requestMap
     * @return
     */
    List<ProductSaleLogDTO> queryProductSaleLogList(Map<String, String> requestMap);
    /**
     * 根据供应商code，查询companyCode
     */
    public String getCompanyCode(@Param("supplierCode")String supplierCode);
}
