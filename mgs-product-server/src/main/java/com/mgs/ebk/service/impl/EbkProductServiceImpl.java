package com.mgs.ebk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.ebk.service.EbkProductService;
import com.mgs.enums.AdjustmentTypeEnum;
import com.mgs.enums.ChannelEnum;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.OverDraftStatusEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.enums.RoomStatusEnum;
import com.mgs.hotel.dto.BasicRoomInfoDTO;
import com.mgs.hotel.remote.HotelRemote;
import com.mgs.product.domain.CompanyChannelPO;
import com.mgs.product.domain.ProductLogPO;
import com.mgs.product.domain.ProductPO;
import com.mgs.product.domain.ProductRestrictPO;
import com.mgs.product.domain.ProductSaleStatusPO;
import com.mgs.product.domain.QuotaAccountPO;
import com.mgs.product.dto.BatchQuotationDTO;
import com.mgs.product.dto.DeleteProductResponseDTO;
import com.mgs.product.dto.HotelProductsDTO;
import com.mgs.product.dto.ProductDTO;
import com.mgs.product.dto.ProductDayQuotationDTO;
import com.mgs.product.dto.ProductForShowDTO;
import com.mgs.product.dto.ProductHotelDTO;
import com.mgs.product.dto.ProductLogDTO;
import com.mgs.product.dto.ProductOrderQueryDTO;
import com.mgs.product.dto.ProductOrderQueryRequestDTO;
import com.mgs.product.dto.ProductRoomDTO;
import com.mgs.product.dto.ProductSaleItemDTO;
import com.mgs.product.dto.ProductTempDTO;
import com.mgs.product.dto.QueryProductRequestDTO;
import com.mgs.product.dto.TotalAmtDTO;
import com.mgs.product.mapper.CompanyChannelMapper;
import com.mgs.product.mapper.EbkProductMapper;
import com.mgs.product.mapper.ProductLogMapper;
import com.mgs.product.mapper.ProductMapper;
import com.mgs.product.mapper.ProductPriceMapper;
import com.mgs.product.mapper.ProductQuotaMapper;
import com.mgs.product.mapper.ProductRestrictMapper;
import com.mgs.product.mapper.ProductSaleStatusMapper;
import com.mgs.product.mapper.QuotaAccountMapper;
import com.mgs.util.BeanUtil;
import com.mgs.util.DateUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.mgs.util.DateUtil.hour_format;

/**
 * @author py
 * @date 2019/8/5 18:00
 **/
@Slf4j
@Service("ebkProductService")
public class EbkProductServiceImpl implements EbkProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private HotelRemote hotelRemote;

    @Autowired
    private EbkProductMapper ebkProductMapper;

    @Override
    public String getCompanyCode(String supplierCode) {
        String companyCode = productMapper.getCompanyCode(supplierCode);
        return companyCode;
    }

    @Override
    public Response queryHotelList(QueryProductRequestDTO queryProductRequestDTO) {
        Response response = new Response(1);
        PaginationSupportDTO<ProductHotelDTO> paginationSupportDTO = new PaginationSupportDTO<ProductHotelDTO>();

        try {
            PageHelper.startPage(queryProductRequestDTO.getCurrentPage(),queryProductRequestDTO.getPageSize());
            List<ProductHotelDTO> productHotelDTOList = ebkProductMapper.queryHotelList(queryProductRequestDTO);
            PageInfo<ProductHotelDTO> page = new PageInfo<ProductHotelDTO>(productHotelDTOList);
            paginationSupportDTO.setItemList(productHotelDTOList);
            paginationSupportDTO.setCurrentPage(page.getPageNum());
            paginationSupportDTO.setPageSize(page.getPageSize());
            paginationSupportDTO.setTotalCount(page.getTotal());
            paginationSupportDTO.setTotalPage(page.getPages());
            response.setModel(paginationSupportDTO);
        }catch (Exception e) {
            log.error("queryHotelList-service error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @Override
    public Response queryHotelProducts(QueryProductRequestDTO queryProductRequestDTO) {
        Response response = new Response(1);
        try {
            HotelProductsDTO hotelProductsDTO = new HotelProductsDTO();
            //查询酒店产品列表
            List<ProductTempDTO> productTempDTOList = ebkProductMapper.queryHotelProducts(queryProductRequestDTO);
            //组装酒店产品数据
            hotelProductsDTO = this.assemblyHotelProducts(productTempDTOList,queryProductRequestDTO.getHotelId());
            response.setModel(hotelProductsDTO);
        }catch (Exception e) {
            log.error("queryHotelProducts-service error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    /**
     * 组装酒店产品数据
     * @param productTempDTOList
     * @return
     */
    private HotelProductsDTO assemblyHotelProducts(List<ProductTempDTO> productTempDTOList,Integer hotelId) {
        HotelProductsDTO hotelProductsDTO = new HotelProductsDTO();
        Map<Integer, ProductRoomDTO> roomMap = new HashMap<Integer,ProductRoomDTO>();//房型
        Map<Integer,Map<Integer, ProductForShowDTO>> roomProductsMap = new HashMap<Integer,Map<Integer,ProductForShowDTO>>();//房型对应产品
        Map<Integer,List<ProductSaleItemDTO>> productSaleItemDTOMap = new HashMap<Integer,List<ProductSaleItemDTO>>();//产品对应行情信息
        List<BasicRoomInfoDTO> roomInfo = new ArrayList<BasicRoomInfoDTO>();
        productTempDTOList.forEach(productTempDTO -> {
            ProductSaleItemDTO productSaleItemDTO = BeanUtil.transformBean(productTempDTO,ProductSaleItemDTO.class);
            //组装酒店信息
            if (null == hotelProductsDTO.getHotelId()) {
                hotelProductsDTO.setHotelId(productTempDTO.getHotelId());
                hotelProductsDTO.setHotelName(productTempDTO.getHotelName());
            }
            //组装房型信息
            if (null == roomMap.get(productTempDTO.getRoomId())) {
                ProductRoomDTO productRoomDTO = new ProductRoomDTO();
                productRoomDTO.setRoomId(productTempDTO.getRoomId());
                productRoomDTO.setRoomName(productTempDTO.getRoomName());
                productRoomDTO.setBedTypes(productTempDTO.getRoomBedTypes());
                roomMap.put(productTempDTO.getRoomId(),productRoomDTO);
            }
            //组装房型产品信息
            if (null == roomProductsMap.get(productTempDTO.getRoomId())) {
                Map<Integer,ProductForShowDTO> productForShowDTOMap = new HashMap<Integer,ProductForShowDTO>();
                ProductForShowDTO productForShowDTO = this.assemblyProduct(productTempDTO);
                productForShowDTOMap.put(productTempDTO.getProductId(),productForShowDTO);
                roomProductsMap.put(productTempDTO.getRoomId(),productForShowDTOMap);
            }else if (null == roomProductsMap.get(productTempDTO.getRoomId()).get(productTempDTO.getProductId())){
                ProductForShowDTO productForShowDTO = this.assemblyProduct(productTempDTO);
                roomProductsMap.get(productTempDTO.getRoomId()).put(productTempDTO.getProductId(),productForShowDTO);
            }
            //组装每日行情信息
            if (null == productSaleItemDTOMap.get(productTempDTO.getProductId())) {
                List<ProductSaleItemDTO> productSaleItemDTOList = new ArrayList<ProductSaleItemDTO>();
                productSaleItemDTOList.add(productSaleItemDTO);
                productSaleItemDTOMap.put(productTempDTO.getProductId(),productSaleItemDTOList);
            }else {
                productSaleItemDTOMap.get(productTempDTO.getProductId()).add(productSaleItemDTO);
            }
        });
        //封装产品到房型
        for (Integer roomId : roomProductsMap.keySet()) {
            List<ProductForShowDTO> productForShowDTOList = new ArrayList<ProductForShowDTO>();
            for (Integer productId : roomProductsMap.get(roomId).keySet()) {
                roomProductsMap.get(roomId).get(productId).setSaleItemList(productSaleItemDTOMap.get(productId));
                productForShowDTOList.add(roomProductsMap.get(roomId).get(productId));
            }
            //产品倒叙排序
            Collections.sort(productForShowDTOList, (ProductForShowDTO p1, ProductForShowDTO p2) -> p2.getProductId() - p1.getProductId());
            roomMap.get(roomId).setProductList(productForShowDTOList);
        }
        //取得没有产品的房型进行封装
        Map<String,String> requestMap  = new HashMap<String, String>();
        requestMap.put("hotelId",String.valueOf(hotelId));
        requestMap.put("currentPage", "1");
        requestMap.put("pageSize", "100");
        Response response = hotelRemote.queryRoomList(requestMap);
        if (response.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != response.getModel()) {
            JSONObject jsonArray = (JSONObject) JSON.parseObject(JSONObject.toJSONString(response.getModel()));
            roomInfo = JSON.parseArray(jsonArray.getString("itemList"), BasicRoomInfoDTO.class);
        }
        //封装房型到酒店
        List<ProductRoomDTO> productRoomDTOList = new ArrayList<ProductRoomDTO>();
        HashMap<Integer, BasicRoomInfoDTO> roomInfoMap = new HashMap<Integer, BasicRoomInfoDTO>();
        for (BasicRoomInfoDTO basicRoomInfoDTO:roomInfo) {
            roomInfoMap.put(basicRoomInfoDTO.getRoomId(),basicRoomInfoDTO);
        }
        for (Integer roomId : roomMap.keySet()) {
            productRoomDTOList.add(roomMap.get(roomId));
            roomInfoMap.remove(roomId);
        }
        for (Integer roomId : roomInfoMap.keySet()) {
            ProductRoomDTO productRoomDTO = new ProductRoomDTO();
            productRoomDTO.setRoomId(roomInfoMap.get(roomId).getRoomId());
            productRoomDTO.setRoomName(roomInfoMap.get(roomId).getRoomName());
            productRoomDTOList.add(productRoomDTO);
        }
        hotelProductsDTO.setRoomList(productRoomDTOList);
        return hotelProductsDTO;
    }

    /**
     * 组装产品数据
     * @param productTempDTO
     * @param productTempDTO
     * @return
     */
    private ProductForShowDTO assemblyProduct(ProductTempDTO productTempDTO) {
        ProductForShowDTO productForShowDTO = BeanUtil.transformBean(productTempDTO,ProductForShowDTO.class);
        return productForShowDTO;
    }

}
