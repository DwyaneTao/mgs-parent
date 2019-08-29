package com.mgs.product.service;

import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.product.domain.ProductLogPO;
import com.mgs.product.dto.BatchQuotationDTO;
import com.mgs.product.dto.ProductDTO;
import com.mgs.product.dto.ProductDayQuotationDTO;
import com.mgs.product.dto.ProductLogDTO;
import com.mgs.product.dto.ProductOrderQueryDTO;
import com.mgs.product.dto.ProductOrderQueryRequestDTO;
import com.mgs.product.dto.ProductSaleLogDTO;
import com.mgs.product.dto.QueryProductRequestDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Owen
 * @Date: 2019/4/24 11:20
 * @Description: 产品
 */
public interface ProductService {

    /**
     * 新增产品
     * @param productDTO
     * @return productId
     */
    Response addProduct(ProductDTO productDTO);

    /**
     * 修改产品
     * @param productDTO
     * @return
     */
    Response modifyProduct(ProductDTO productDTO);

    /**
     * 删除产品
     * @param requestMap
     * @return
     */
    Response deleteProduct(Map<String,String> requestMap);

    /**
     * 根据产品Id查询产品
     * @param requestMap
     * @return
     */
    Response queryProduct(Map<String,String> requestMap);

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

    /**
     * 单日修改产品价格、房态
     * @param productDayQuotationDTO
     * @return
     */
    Response dailyModifyProductInfo(ProductDayQuotationDTO productDayQuotationDTO);

    /**
     * 批量修改价格
     * @param batchQuotationDTOList
     * @return
     */
    Response batchModifyBasePrice(List<BatchQuotationDTO> batchQuotationDTOList);

    /**
     * 批量修改配额、房态
     * @param batchQuotationDTOList
     * @return
     */
    Response batchModifyRoomStatus(List<BatchQuotationDTO> batchQuotationDTOList);
    /**
     * 查询产品list给订单用
     */
    PaginationSupportDTO<ProductOrderQueryDTO> queryOrderProductList(ProductOrderQueryRequestDTO productOrderQueryRequestDTO);

    /**
     * 查询产品日志
     * @param requestMap
     * @return
     */
    PageInfo<ProductLogDTO> queryProductLogList(Map<String, String> requestMap);

    /**
     * 修改产品停售状态
     * @param requestMap
     * @return
     */
    int modifyOffShelveStatus(Map<String, String> requestMap);


}
