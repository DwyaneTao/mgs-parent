package com.mgs.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.dis.dto.DisMappingQueryDTO;
import com.mgs.dis.dto.DisProductMappingDTO;
import com.mgs.dis.dto.IncrementDTO;
import com.mgs.dis.dto.ProductSaleIncreaseDTO;
import com.mgs.dis.dto.ProductSaleStatusDTO;
import com.mgs.dis.remote.DisMappingRemote;
import com.mgs.dis.remote.InitRemote;
import com.mgs.enums.ChannelEnum;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.IncrementTypeEnum;
import com.mgs.enums.ProductSaleOperationEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.enums.SaleAdjustmentTypeEnum;
import com.mgs.enums.SaleStatusEnum;
import com.mgs.finance.remote.ExchangeRateRemote;
import com.mgs.finance.remote.request.ExchangeRateDTO;
import com.mgs.keys.ChannelCodeKey;
import com.mgs.keys.RedisKey;
import com.mgs.product.domain.*;
import com.mgs.product.dto.*;
import com.mgs.product.mapper.*;
import com.mgs.product.service.ProductSaleService;
import com.mgs.util.BeanUtil;
import com.mgs.util.DateUtil;
import com.mgs.util.RedisUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: Owen
 * @Date: 2019/4/24 11:21
 * @Description: 产品销售service实现类
 */
@Slf4j
@Service("productSaleService")
public class ProductSaleServiceImpl implements ProductSaleService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductPriceMapper productPriceMapper;

    @Autowired
    private ProductQuotaMapper productQuotaMapper;

    @Autowired
    private ProductDayIncreaseMapper productDayIncreaseMapper;

    @Autowired
    private ProductSaleStatusMapper productSaleStatusMapper;

    @Autowired
    private ProductIncreaseMapper productIncreaseMapper;

    @Autowired
    private ExchangeRateRemote exchangeRateRemote;

    @Autowired
    private ProductSaleLogMapper productSaleLogMapper;

    @Autowired
    private DisMappingRemote disMappingRemote;

    @Autowired
    private InitRemote initRemote;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Response queryHotelList(QueryProductRequestDTO queryProductRequestDTO) {
        Response response = new Response(1);
        PaginationSupportDTO<HotelProductsDTO> paginationSupportDTO = new PaginationSupportDTO<HotelProductsDTO>();

        try {
            PageHelper.startPage(queryProductRequestDTO.getCurrentPage(), queryProductRequestDTO.getPageSize());
            List<Integer> saleHotelIds = productMapper.querySaleHotelIds(queryProductRequestDTO);
            if (!CollectionUtils.isEmpty(saleHotelIds)) {
                queryProductRequestDTO.setHotelIdList(saleHotelIds);
                //查询底价，加幅，房态，配额等数据
                List<ProductTempDTO> productTempDTOList = productMapper.querySaleProducts(queryProductRequestDTO);
                //如果是代理通渠道，查询代理通渠道产品是否已经推送
                List<DisProductMappingDTO> dltProductMappingList = null;
                if (queryProductRequestDTO.getChannelCode().equals(ChannelEnum.CTRIP.key)) {
                    DisMappingQueryDTO disMappingQueryDTO = new DisMappingQueryDTO();
                    disMappingQueryDTO.setHotelId(queryProductRequestDTO.getHotelId());
                    disMappingQueryDTO.setCompanyCode(queryProductRequestDTO.getCompanyCode());
                    disMappingQueryDTO.setDistributor(ChannelCodeKey.ctripCode);
                    dltProductMappingList = disMappingRemote.queryProductMapping(disMappingQueryDTO);
                }

                List<HotelProductsDTO> hotelProductsDTOList = this.assemblySaleHotelProduct(productTempDTOList, saleHotelIds, queryProductRequestDTO.getChannelCode(),dltProductMappingList);

                PageInfo<Integer> page = new PageInfo<Integer>(saleHotelIds);
                paginationSupportDTO.setItemList(hotelProductsDTOList);
                paginationSupportDTO.setCurrentPage(page.getPageNum());
                paginationSupportDTO.setPageSize(page.getPageSize());
                paginationSupportDTO.setTotalCount(page.getTotal());
                paginationSupportDTO.setTotalPage(page.getPages());
                response.setModel(paginationSupportDTO);
            }
        } catch (Exception e) {
            log.error("queryHotelList-service error!", e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @Override
    public Response querySalePriceList(QueryProductRequestDTO queryProductRequestDTO) {
        Response response = new Response(1);
        ProductSalePriceDTO productSalePriceDTO = null;

        try {
            //获取日期列表
            Map<Date, Date> dateMap = DateUtil.getDateMap(DateUtil.stringToDate(queryProductRequestDTO.getStartDate()), DateUtil.stringToDate(queryProductRequestDTO.getEndDate()));
            //查询产品
            ProductDTO productDTO = productMapper.queryProduct(queryProductRequestDTO.getProductId());
            productSalePriceDTO = BeanUtil.transformBean(productDTO, ProductSalePriceDTO.class);
            //查询底价
            Example priceExample = new Example(ProductPricePO.class);
            Example.Criteria priceCriteria = priceExample.createCriteria();
            priceCriteria.andEqualTo("productId", queryProductRequestDTO.getProductId());
            priceCriteria.andBetween("saleDate", queryProductRequestDTO.getStartDate(), queryProductRequestDTO.getEndDate());
            List<ProductPricePO> pricePOList = productPriceMapper.selectByExample(priceExample);
            //查询配额和房态
            List<ProductQuotaPO> productQuotaPOList = null;
            if (null != productDTO && null != productDTO.getQuotaAccountId()) {
                Example quotaExample = new Example(ProductQuotaPO.class);
                Example.Criteria quotaCriteria = quotaExample.createCriteria();
                quotaCriteria.andEqualTo("quotaAccountId", productDTO.getQuotaAccountId());
                quotaCriteria.andBetween("saleDate", queryProductRequestDTO.getStartDate(), queryProductRequestDTO.getEndDate());
                productQuotaPOList = productQuotaMapper.selectByExample(quotaExample);
            }
            //查询加幅
            Example salePriceExample = new Example(ProductDayIncreasePO.class);
            Example.Criteria salePriceCriteria = salePriceExample.createCriteria();
            salePriceCriteria.andEqualTo("productId", queryProductRequestDTO.getProductId());
            salePriceCriteria.andEqualTo("companyCode", queryProductRequestDTO.getCompanyCode());
            salePriceCriteria.andBetween("saleDate", queryProductRequestDTO.getStartDate(), queryProductRequestDTO.getEndDate());
            List<ProductDayIncreasePO> productDayIncreasePOList = productDayIncreaseMapper.selectByExample(salePriceExample);
            //组装数据
            this.assemblyProductSalePrice(dateMap, productSalePriceDTO, pricePOList, productQuotaPOList, productDayIncreasePOList, queryProductRequestDTO.getChannelCode(), productDTO.getCurrency());
            response.setModel(productSalePriceDTO);
        } catch (Exception e) {
            log.error("querySalePriceList-service error!", e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @Override
    @Transactional
    public Response singleProductModifySaleStatus(@RequestBody Map<String, String> requestMap) {
        Response response = new Response(1);
        try {
            ProductSaleStatusPO productSaleStatusPO = new ProductSaleStatusPO();
            productSaleStatusPO.setModifiedBy(requestMap.get("modifiedBy"));
            productSaleStatusPO.setModifiedDt(requestMap.get("modifiedDt"));
            productSaleStatusPO.setProductId(Integer.valueOf(requestMap.get("productId")));
            productSaleStatusPO.setCompanyCode(requestMap.get("companyCode"));
            switch (ChannelEnum.getNoByKey(requestMap.get("channelCode"))) {
                case 0:
                    productSaleStatusPO.setB2bSaleStatus(Integer.valueOf(requestMap.get("saleStatus")));
                    break;
                case 1:
                    productSaleStatusPO.setB2cSaleStatus(Integer.valueOf(requestMap.get("saleStatus")));
                    break;
                case 2:
                    productSaleStatusPO.setCtripSaleStatus(Integer.valueOf(requestMap.get("saleStatus")));
                    break;
                case 3:
                    productSaleStatusPO.setQunarSaleStatus(Integer.valueOf(requestMap.get("saleStatus")));
                    break;
                case 4:
                    productSaleStatusPO.setMeituanSaleStatus(Integer.valueOf(requestMap.get("saleStatus")));
                    break;
                case 5:
                    productSaleStatusPO.setFeizhuSaleStatus(Integer.valueOf(requestMap.get("saleStatus")));
                    break;
                case 6:
                    productSaleStatusPO.setTcylSaleStatus(Integer.valueOf(requestMap.get("saleStatus")));
                    break;
                default:
            }

            //TODO 比较数据是否变化
            List<ProductSaleStatusPO> productSaleStatusList = new ArrayList<>(Arrays.asList(productSaleStatusPO));
            handlerProductSaleStatus(productSaleStatusList);

            if(CollectionUtils.isNotEmpty(productSaleStatusList)) {
                Example productSaleStatusExample = new Example(ProductSaleStatusPO.class);
                Example.Criteria criteria = productSaleStatusExample.createCriteria();
                criteria.andEqualTo("productId", requestMap.get("productId"));
                criteria.andEqualTo("companyCode", requestMap.get("companyCode"));
                productSaleStatusMapper.updateByExampleSelective(productSaleStatusPO, productSaleStatusExample);

                ProductSaleLogPO productSaleLogPO = new ProductSaleLogPO();
                productSaleLogPO.setChannelCode(requestMap.get("channelCode"));
                productSaleLogPO.setCompanyCode(requestMap.get("companyCode"));
                productSaleLogPO.setContent("操作\"" + SaleStatusEnum.getDesc(Integer.valueOf(requestMap.get("saleStatus"))) + "\"");
                productSaleLogPO.setOperationType(ProductSaleOperationEnum.MODIFY_SALE.no);
                productSaleLogPO.setCreatedBy(requestMap.get("modifiedBy"));
                productSaleLogPO.setCreatedDt(requestMap.get("modifiedDt"));
                productSaleLogPO.setProductId(Integer.valueOf(requestMap.get("productId")));
                productSaleLogMapper.insert(productSaleLogPO);
            }
        } catch (Exception e) {
            log.error("singleProductModifySaleStatus-service error!", e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @Override
    @Transactional
    public Response dailyModifySalePrice(@RequestBody Map<String, String> requestMap) {
        Response response = new Response(1);
        try {
            //组装单日加幅
            //将百分比正确化
            if (requestMap.get("adjustmentType").equals("2")) {
                String modifiedAmt = requestMap.get("modifiedAmt");
                Double aDouble = Double.valueOf(modifiedAmt);
                aDouble = aDouble / 100;
                String s = String.valueOf(aDouble);
                requestMap.put("modifiedAmt", s);
            }
            ProductDayIncreasePO productDayIncreasePO = this.assemblyDayIncrease(requestMap);

            List<ProductDayIncreasePO> productDayIncreaseList = Arrays.asList(productDayIncreasePO);
            handlerProductIncrease(productDayIncreaseList);

            if(CollectionUtils.isNotEmpty(productDayIncreaseList)) {
                //查询单日加幅
                Example example = new Example(ProductDayIncreasePO.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("productId", requestMap.get("productId"));
                criteria.andEqualTo("companyCode", requestMap.get("companyCode"));
                criteria.andEqualTo("saleDate", requestMap.get("saleDate"));
                List<ProductDayIncreasePO> productDayIncreasePOList = productDayIncreaseMapper.selectByExample(example);
                if (CollectionUtils.isEmpty(productDayIncreasePOList)) {//新增单日加幅
                    productDayIncreaseMapper.insert(productDayIncreasePO);
                } else {//修改单日加幅
                    productDayIncreaseMapper.updateByExampleSelective(productDayIncreasePO, example);
                }
                //组装加幅总表数据
                ProductIncreasePO productIncreasePO = this.assemblySingleIncrease(requestMap);
                //插入加幅总表数据
                productIncreaseMapper.insert(productIncreasePO);

                ProductSaleLogPO productSaleLogPO = new ProductSaleLogPO();
                productSaleLogPO.setCreatedDt(requestMap.get("modifiedDt"));
                productSaleLogPO.setCreatedBy(requestMap.get("modifiedBy"));
                productSaleLogPO.setOperationType(ProductSaleOperationEnum.SALE.no);
                productSaleLogPO.setStartDate(requestMap.get("saleDate"));
                productSaleLogPO.setEndDate(requestMap.get("saleDate"));
                productSaleLogPO.setContent(StringUtil.isValidString(requestMap.get(requestMap.get("modifiedAmt"))) ? "" : SaleAdjustmentTypeEnum.getDesc(Integer.valueOf(requestMap.get("adjustmentType"))).replace("#", requestMap.get("modifiedAmt")));
                productSaleLogPO.setCompanyCode(requestMap.get("companyCode"));
                productSaleLogPO.setChannelCode(requestMap.get("channelCode"));
                productSaleLogPO.setProductId(Integer.valueOf(requestMap.get("productId")));
                productSaleLogMapper.insert(productSaleLogPO);

                initRemote.initSalePrice(productDayIncreasePOList.stream().map(i -> BeanUtil.transformBean(i, ProductSaleIncreaseDTO.class)).collect(Collectors.toList()));
            }
        } catch (Exception e) {
            log.error("dailyModifySalePrice-service error!", e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @Override
    @Transactional
    public Response batchModifySalePrice(@RequestBody BatchSaleDTO batchSaleDTO) {
        Response response = new Response(1);
        try {
            List<ProductSaleLogPO> productSaleLogPOList = new ArrayList<ProductSaleLogPO>();
            for (int i = 0; i < batchSaleDTO.getItemList().size(); i++) {
                if (batchSaleDTO.getItemList().get(i).getAdjustmentType().equals(2)) {
                    batchSaleDTO.getItemList().get(i).setModifiedAmt(batchSaleDTO.getItemList().get(i).getModifiedAmt().divide(new BigDecimal(100)));
                }

                ProductSaleLogPO productSaleLogPO = new ProductSaleLogPO();
                productSaleLogPO.setCreatedDt(batchSaleDTO.getModifiedDt());
                productSaleLogPO.setCreatedBy(batchSaleDTO.getModifiedBy());
                productSaleLogPO.setOperationType(ProductSaleOperationEnum.SALE.no);
                productSaleLogPO.setStartDate(batchSaleDTO.getItemList().get(i).getStartDate());
                productSaleLogPO.setEndDate(batchSaleDTO.getItemList().get(i).getEndDate());
                productSaleLogPO.setContent(batchSaleDTO.getItemList().get(i).getModifiedAmt() == null ? "" : SaleAdjustmentTypeEnum.getDesc(Integer.valueOf(batchSaleDTO.getItemList().get(i).getAdjustmentType())).replace("#", batchSaleDTO.getItemList().get(i).getModifiedAmt().toString()));
                productSaleLogPO.setCompanyCode(batchSaleDTO.getCompanyCode());
                productSaleLogPO.setChannelCode(batchSaleDTO.getChannelCode());
                productSaleLogPO.setProductId(batchSaleDTO.getItemList().get(i).getProductId());
                productSaleLogPOList.add(productSaleLogPO);
            }
            //组装加幅总表数据
            List<ProductIncreasePO> productIncreasePOList = this.assemblyIncrease(batchSaleDTO);
            //组装加幅每日表数据
            List<ProductDayIncreasePO> productDayIncreasePOList = this.assemblyDayIncreaseList(batchSaleDTO);

            //TODO 处理加幅数据
            handlerProductIncrease(productDayIncreasePOList);

            if(CollectionUtils.isNotEmpty(productDayIncreasePOList)) {
                //加幅总表数据入库
                if (!CollectionUtils.isEmpty(productIncreasePOList)) {
                    productIncreaseMapper.insertList(productIncreasePOList);
                }
                //加幅每日表数据入库
                if (CollectionUtils.isNotEmpty(productDayIncreasePOList)) {
                    productDayIncreaseMapper.mergeProductDayIncrease(productDayIncreasePOList);
                }

                if (!CollectionUtils.isEmpty(productSaleLogPOList)) {
                    productSaleLogMapper.insertList(productSaleLogPOList);
                }

                initRemote.initSalePrice(productDayIncreasePOList.stream().map(i -> BeanUtil.transformBean(i, ProductSaleIncreaseDTO.class)).collect(Collectors.toList()));
            }
        } catch (Exception e) {
            log.error("batchModifySalePrice-service error!", e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @Override
    @Transactional
    public Response batchModifySaleStatus(@RequestBody BatchSaleDTO batchSaleDTO) {
        Response response = new Response(1);
        try {
            List<ProductSaleStatusPO> productSaleStatusPOList = new ArrayList<ProductSaleStatusPO>();
            List<ProductSaleLogPO> productSaleLogPOList = new ArrayList<ProductSaleLogPO>();
            if (null != batchSaleDTO && !CollectionUtils.isEmpty(batchSaleDTO.getItemList())) {
                for (BatchSaleItemDTO batchSaleItemDTO : batchSaleDTO.getItemList()) {
                    ProductSaleStatusPO productSaleStatusPO = new ProductSaleStatusPO();
                    productSaleStatusPO.setModifiedDt(batchSaleDTO.getModifiedDt());
                    productSaleStatusPO.setModifiedBy(batchSaleDTO.getModifiedBy());
                    productSaleStatusPO.setProductId(batchSaleItemDTO.getProductId());
                    productSaleStatusPO.setCompanyCode(batchSaleDTO.getCompanyCode());
                    switch (ChannelEnum.getNoByKey(batchSaleDTO.getChannelCode())) {
                        case 0:
                            productSaleStatusPO.setB2bSaleStatus(batchSaleDTO.getSaleStatus());
                            break;
                        case 1:
                            productSaleStatusPO.setB2cSaleStatus(batchSaleDTO.getSaleStatus());
                            break;
                        case 2:
                            productSaleStatusPO.setCtripSaleStatus(batchSaleDTO.getSaleStatus());
                            break;
                        case 3:
                            productSaleStatusPO.setQunarSaleStatus(batchSaleDTO.getSaleStatus());
                            break;
                        case 4:
                            productSaleStatusPO.setMeituanSaleStatus(batchSaleDTO.getSaleStatus());
                            break;
                        case 5:
                            productSaleStatusPO.setFeizhuSaleStatus(batchSaleDTO.getSaleStatus());
                            break;
                        case 6:
                            productSaleStatusPO.setTcylSaleStatus(batchSaleDTO.getSaleStatus());
                            break;
                        default:
                    }

                    productSaleStatusPOList.add(productSaleStatusPO);

                    ProductSaleLogPO productSaleLogPO = new ProductSaleLogPO();
                    productSaleLogPO.setChannelCode(batchSaleDTO.getChannelCode());
                    productSaleLogPO.setCompanyCode(batchSaleDTO.getCompanyCode());
                    productSaleLogPO.setContent("操作\"" + SaleStatusEnum.getDesc(batchSaleDTO.getSaleStatus()) + "\"");
                    productSaleLogPO.setOperationType(ProductSaleOperationEnum.MODIFY_SALE.no);
                    productSaleLogPO.setCreatedBy(batchSaleDTO.getModifiedBy());
                    productSaleLogPO.setCreatedDt(batchSaleDTO.getModifiedDt());
                    productSaleLogPO.setProductId(batchSaleItemDTO.getProductId());
                    productSaleLogPOList.add(productSaleLogPO);
                }
            }

            //TODO 过滤数据
            handlerProductSaleStatus(productSaleStatusPOList);

            if(CollectionUtils.isNotEmpty(productSaleStatusPOList)){
                if (productSaleStatusPOList.size() > 0) {
                    productSaleStatusMapper.batchModifyProductSaleStatus(productSaleStatusPOList);
                }

                if (productSaleLogPOList.size() > 0) {
                    productSaleLogMapper.insertList(productSaleLogPOList);
                }

                List<ProductSaleStatusDTO> productSaleStatusList = productSaleStatusPOList.stream().map(i -> BeanUtil.transformBean(i, ProductSaleStatusDTO.class)).collect(Collectors.toList());
                initRemote.initSaleStatus(productSaleStatusList);
            }
        } catch (Exception e) {
            log.error("batchModifySaleStatus-service error!", e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    /**
     * 组装酒店售卖产品数据
     *
     * @param productTempDTOList
     * @param saleHotelIds
     * @param channelCode
     * @return
     */
    private List<HotelProductsDTO> assemblySaleHotelProduct(List<ProductTempDTO> productTempDTOList, List<Integer> saleHotelIds, String channelCode, List<DisProductMappingDTO> dltProductMappingList) {
        Map<Integer, HotelProductsDTO> hotelMap = new HashMap<Integer, HotelProductsDTO>();
        //酒店房型
        Map<Integer, Map<Integer, ProductRoomDTO>> roomMap = new HashMap<Integer, Map<Integer, ProductRoomDTO>>();
        //房型产品
        Map<Integer, Map<Integer, ProductForShowDTO>> productForShowDTOMap = new HashMap<Integer, Map<Integer, ProductForShowDTO>>();
        //产品渠道推送状态
        Map<Integer,Integer> productMappingMap = new HashMap<Integer,Integer>();
        if (!CollectionUtils.isEmpty(dltProductMappingList)) {
            dltProductMappingList.forEach(disProductMappingDTO -> {
                productMappingMap.put(disProductMappingDTO.getProductId(),disProductMappingDTO.getProductId());
            });
        }
        productTempDTOList.forEach(productTempDTO -> {
            if (null == roomMap.get(productTempDTO.getHotelId())) {
                //房型产品
                ProductForShowDTO productForShowDTO = this.assemblyProductForShow(productTempDTO, channelCode,productMappingMap);
                Map<Integer, ProductForShowDTO> pMap = new HashMap<Integer, ProductForShowDTO>();
                pMap.put(productTempDTO.getProductId(), productForShowDTO);
                productForShowDTOMap.put(productTempDTO.getRoomId(), pMap);

                //酒店房型
                ProductRoomDTO productRoomDTO = new ProductRoomDTO();
                productRoomDTO.setRoomId(productTempDTO.getRoomId());
                productRoomDTO.setRoomName(productTempDTO.getRoomName());
                Map<Integer, ProductRoomDTO> mMap = new HashMap<Integer, ProductRoomDTO>();
                mMap.put(productTempDTO.getRoomId(), productRoomDTO);
                roomMap.put(productTempDTO.getHotelId(), mMap);

                //酒店
                HotelProductsDTO hotelProductsDTO = new HotelProductsDTO();
                hotelProductsDTO.setHotelId(productTempDTO.getHotelId());
                hotelProductsDTO.setHotelName(productTempDTO.getHotelName());
                hotelMap.put(productTempDTO.getHotelId(), hotelProductsDTO);
            } else if (null == roomMap.get(productTempDTO.getHotelId()).get(productTempDTO.getRoomId())) {
                //房型产品
                ProductForShowDTO productForShowDTO = this.assemblyProductForShow(productTempDTO, channelCode,productMappingMap);
                Map<Integer, ProductForShowDTO> pMap = new HashMap<Integer, ProductForShowDTO>();
                pMap.put(productTempDTO.getProductId(), productForShowDTO);
                productForShowDTOMap.put(productTempDTO.getRoomId(), pMap);

                //酒店房型
                ProductRoomDTO productRoomDTO = new ProductRoomDTO();
                productRoomDTO.setRoomId(productTempDTO.getRoomId());
                productRoomDTO.setRoomName(productTempDTO.getRoomName());
                roomMap.get(productTempDTO.getHotelId()).put(productTempDTO.getRoomId(), productRoomDTO);
            } else {
                //房型产品
                ProductForShowDTO productForShowDTO = this.assemblyProductForShow(productTempDTO, channelCode,productMappingMap);
                productForShowDTOMap.get(productTempDTO.getRoomId()).put(productTempDTO.getProductId(), productForShowDTO);
            }
        });

        //封装数据
        List<HotelProductsDTO> hotelProductsDTOList = new ArrayList<HotelProductsDTO>();
        for (Integer hotelId : saleHotelIds) {
            HotelProductsDTO hotelProductsDTO = hotelMap.get(hotelId);
            List<ProductRoomDTO> productRoomDTOList = new ArrayList<ProductRoomDTO>();
            for (Integer roomId : roomMap.get(hotelId).keySet()) {
                ProductRoomDTO productRoomDTO = roomMap.get(hotelId).get(roomId);
                List<ProductForShowDTO> productForShowDTOList = new ArrayList<ProductForShowDTO>();
                for (Integer productId : productForShowDTOMap.get(roomId).keySet()) {
                    productForShowDTOList.add(productForShowDTOMap.get(roomId).get(productId));
                }
                productRoomDTO.setProductList(productForShowDTOList);
                productRoomDTOList.add(productRoomDTO);
            }
            hotelProductsDTO.setRoomList(productRoomDTOList);
            hotelProductsDTOList.add(hotelProductsDTO);
        }

        return hotelProductsDTOList;
    }

    /**
     * 组装产品
     *
     * @param productTempDTO
     * @param channelCode
     * @return
     */
    private ProductForShowDTO assemblyProductForShow(ProductTempDTO productTempDTO, String channelCode, Map<Integer,Integer> productMappingMap) {
        ProductForShowDTO productForShowDTO = BeanUtil.transformBean(productTempDTO, ProductForShowDTO.class);
        if (null != productMappingMap && null != productMappingMap.get(productForShowDTO.getProductId())) {
            productForShowDTO.setChannelSaleStatus(1);//OTA渠道销售中
        }else {
            productForShowDTO.setChannelSaleStatus(0);//OTA渠道未销售
        }
        switch (ChannelEnum.getNoByKey(channelCode)) {
            case 0:
                productForShowDTO.setSaleStatus(productTempDTO.getB2bSaleStatus());
                break;
            case 1:
                productForShowDTO.setSaleStatus(productTempDTO.getB2cSaleStatus());
                break;
            case 2:
                productForShowDTO.setSaleStatus(productTempDTO.getCtripSaleStatus());
                break;
            case 3:
                productForShowDTO.setSaleStatus(productTempDTO.getQunarSaleStatus());
                break;
            case 4:
                productForShowDTO.setSaleStatus(productTempDTO.getMeituanSaleStatus());
                break;
            case 5:
                productForShowDTO.setSaleStatus(productTempDTO.getFeizhuSaleStatus());
                break;
            case 6:
                productForShowDTO.setSaleStatus(productTempDTO.getTcylSaleStatus());
                break;
            default:
                productForShowDTO.setSaleStatus(0);
        }
        return productForShowDTO;
    }

    /**
     * 组装产品售卖信息
     *
     * @param dateMap
     * @param productSalePriceDTO
     * @param pricePOList
     * @param productQuotaPOList
     * @param productDayIncreasePOList
     */
    private void assemblyProductSalePrice(Map<Date, Date> dateMap, ProductSalePriceDTO productSalePriceDTO, List<ProductPricePO> pricePOList,
                                          List<ProductQuotaPO> productQuotaPOList, List<ProductDayIncreasePO> productDayIncreasePOList, String channelCode, Integer currency) {
        Map<Date, ProductSalePriceItemDTO> productSaleItemDTOMap = new TreeMap<Date, ProductSalePriceItemDTO>(new Comparator<Date>() {
            public int compare(Date obj1, Date obj2) {
                // 升序排序
                return obj1.compareTo(obj2);
            }
        });
        for (Date date : dateMap.keySet()) {
            ProductSalePriceItemDTO productSalePriceItemDTO = new ProductSalePriceItemDTO();
            productSalePriceItemDTO.setSaleDate(DateUtil.dateToString(date));
            productSaleItemDTOMap.put(date, productSalePriceItemDTO);
        }

        //查询币种对应的汇率
        BigDecimal rate = null;
        if (currency == 0) {//0为人民币默认汇率为1，不用查询汇率接口
            rate = BigDecimal.ONE;
        } else {
            ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
            exchangeRateDTO.setCurrency(currency);
            Response ExchangeRate = exchangeRateRemote.queryExchangeRate(exchangeRateDTO);
            if (ExchangeRate.getResult().equals(ResultCodeEnum.SUCCESS.code) && null != ExchangeRate.getModel()) {
                JSONArray jsonArray = (JSONArray) JSON.parseArray(JSONObject.toJSONString(ExchangeRate.getModel()));
                Iterator<Object> it = jsonArray.iterator();
                while (it.hasNext()) {
                    JSONObject arrayObj = (JSONObject) it.next();
                    rate = new BigDecimal(arrayObj.get("reversedExchangeRate").toString());
                }
            }
        }
        //组装底价
        if (!CollectionUtils.isEmpty(pricePOList)) {
            for (ProductPricePO productPricePO : pricePOList) {
                productSaleItemDTOMap.get(DateUtil.stringToDate(productPricePO.getSaleDate())).setBasePrice(productPricePO.getBasePrice());
                productSaleItemDTOMap.get(DateUtil.stringToDate(productPricePO.getSaleDate())).setEquivalentBasePrice(productPricePO.getBasePrice().multiply(rate));
            }
        }

        //组装配额和房态
        if (!CollectionUtils.isEmpty(productQuotaPOList)) {
            for (ProductQuotaPO productQuotaPO : productQuotaPOList) {
                productSaleItemDTOMap.get(DateUtil.stringToDate(productQuotaPO.getSaleDate())).setQuota(productQuotaPO.getQuota());
                productSaleItemDTOMap.get(DateUtil.stringToDate(productQuotaPO.getSaleDate())).setRemainingQuota(productQuotaPO.getRemainingQuota());
                productSaleItemDTOMap.get(DateUtil.stringToDate(productQuotaPO.getSaleDate())).setOverDraftStatus(productQuotaPO.getOverDraftStatus());
                productSaleItemDTOMap.get(DateUtil.stringToDate(productQuotaPO.getSaleDate())).setRoomStatus(productQuotaPO.getRoomStatus());
            }
        }

        //组装售价和利润
        if (!CollectionUtils.isEmpty(productDayIncreasePOList)) {
            for (ProductDayIncreasePO productDayIncreasePO : productDayIncreasePOList) {
                //有底价才进行价格计算
                if (null != productSaleItemDTOMap.get(DateUtil.stringToDate(productDayIncreasePO.getSaleDate())).getBasePrice()) {
                    BigDecimal basePrice = productSaleItemDTOMap.get(DateUtil.stringToDate(productDayIncreasePO.getSaleDate())).getBasePrice();//底价
                    Integer adjustmentType = null;//调整方式（0加数值 1减数值 2加百分比 3等于）
                    BigDecimal modifiedAmt = null;//调整金额
                    switch (ChannelEnum.getNoByKey(channelCode)) {
                        case 0:
                            adjustmentType = productDayIncreasePO.getB2bAdjustmentType();
                            modifiedAmt = productDayIncreasePO.getB2bModifiedAmt();
                            break;
                        case 1:
                            adjustmentType = productDayIncreasePO.getB2cAdjustmentType();
                            modifiedAmt = productDayIncreasePO.getB2cModifiedAmt();
                            break;
                        case 2:
                            adjustmentType = productDayIncreasePO.getCtripAdjustmentType();
                            modifiedAmt = productDayIncreasePO.getCtripModifiedAmt();
                            break;
                        case 3:
                            adjustmentType = productDayIncreasePO.getQunarAdjustmentType();
                            modifiedAmt = productDayIncreasePO.getQunarModifiedAmt();
                            break;
                        case 4:
                            adjustmentType = productDayIncreasePO.getMeituanAdjustmentType();
                            modifiedAmt = productDayIncreasePO.getMeituanModifiedAmt();
                            break;
                        case 5:
                            adjustmentType = productDayIncreasePO.getFeizhuAdjustmentType();
                            modifiedAmt = productDayIncreasePO.getFeizhuModifiedAmt();
                            break;
                        case 6:
                            adjustmentType = productDayIncreasePO.getTcylAdjustmentType();
                            modifiedAmt = productDayIncreasePO.getTcylModifiedAmt();
                            break;
                        default:
                    }

                    //没有加幅方式或者没有加幅金额，则没有售价和利润
                    if (null != adjustmentType && null != modifiedAmt) {
                        BigDecimal zero = new BigDecimal("0");
                        switch (adjustmentType) {
                            case 0:
                                productSaleItemDTOMap.get(DateUtil.stringToDate(productDayIncreasePO.getSaleDate())).setSalePrice(basePrice.multiply(rate).add(modifiedAmt).setScale(2, BigDecimal.ROUND_HALF_DOWN));
                                productSaleItemDTOMap.get(DateUtil.stringToDate(productDayIncreasePO.getSaleDate())).setProfit(modifiedAmt);
                                break;
                            case 1:
                                productSaleItemDTOMap.get(DateUtil.stringToDate(productDayIncreasePO.getSaleDate())).setSalePrice(basePrice.multiply(rate).subtract(modifiedAmt).compareTo(zero) > 0 ? basePrice.multiply(rate).subtract(modifiedAmt).setScale(2, BigDecimal.ROUND_HALF_DOWN) : zero);
                                productSaleItemDTOMap.get(DateUtil.stringToDate(productDayIncreasePO.getSaleDate())).setProfit(basePrice.multiply(rate).subtract(modifiedAmt).compareTo(zero) > 0 ? zero.subtract(modifiedAmt).setScale(2, BigDecimal.ROUND_HALF_DOWN) : zero.subtract(basePrice).setScale(2, BigDecimal.ROUND_HALF_DOWN));
                                break;
                            case 2:
                                productSaleItemDTOMap.get(DateUtil.stringToDate(productDayIncreasePO.getSaleDate())).setSalePrice(basePrice.multiply(rate).add(basePrice.multiply(modifiedAmt)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
                                productSaleItemDTOMap.get(DateUtil.stringToDate(productDayIncreasePO.getSaleDate())).setProfit(basePrice.multiply(rate).multiply(modifiedAmt).setScale(2, BigDecimal.ROUND_HALF_DOWN));
                                break;
                            case 3:
                                productSaleItemDTOMap.get(DateUtil.stringToDate(productDayIncreasePO.getSaleDate())).setSalePrice(modifiedAmt);
                                productSaleItemDTOMap.get(DateUtil.stringToDate(productDayIncreasePO.getSaleDate())).setProfit(modifiedAmt.subtract(basePrice.multiply(rate)).setScale(2, BigDecimal.ROUND_HALF_DOWN));
                                break;
                            default:
                        }
                    }
                }
            }
        }
        List<ProductSalePriceItemDTO> productSaleItemDTOList = new ArrayList<ProductSalePriceItemDTO>();
        for (Date date : productSaleItemDTOMap.keySet()) {
            productSaleItemDTOList.add(productSaleItemDTOMap.get(date));
        }
        productSalePriceDTO.setPriceList(productSaleItemDTOList);
    }

    /**
     * 组装每日售卖信息
     *
     * @param requestMap
     * @return
     */
    private ProductDayIncreasePO assemblyDayIncrease(Map<String, String> requestMap) {
        ProductDayIncreasePO productDayIncreasePO = new ProductDayIncreasePO();
        productDayIncreasePO.setCreatedBy(requestMap.get("modifiedBy"));
        productDayIncreasePO.setModifiedBy(requestMap.get("modifiedBy"));
        productDayIncreasePO.setCreatedDt(requestMap.get("modifiedDt"));
        productDayIncreasePO.setModifiedDt(requestMap.get("modifiedDt"));
        productDayIncreasePO.setProductId(Integer.valueOf(requestMap.get("productId")));
        productDayIncreasePO.setCompanyCode(requestMap.get("companyCode"));
        productDayIncreasePO.setSaleDate(requestMap.get("saleDate"));

        switch (ChannelEnum.getNoByKey(requestMap.get("channelCode"))) {
            case 0:
                productDayIncreasePO.setB2bAdjustmentType(Integer.valueOf(requestMap.get("adjustmentType")));
                productDayIncreasePO.setB2bModifiedAmt(new BigDecimal(requestMap.get("modifiedAmt")));
                break;
            case 1:
                productDayIncreasePO.setB2cAdjustmentType(Integer.valueOf(requestMap.get("adjustmentType")));
                productDayIncreasePO.setB2cModifiedAmt(new BigDecimal(requestMap.get("modifiedAmt")));
                break;
            case 2:
                productDayIncreasePO.setCtripAdjustmentType(Integer.valueOf(requestMap.get("adjustmentType")));
                productDayIncreasePO.setCtripModifiedAmt(new BigDecimal(requestMap.get("modifiedAmt")));
                break;
            case 3:
                productDayIncreasePO.setQunarAdjustmentType(Integer.valueOf(requestMap.get("adjustmentType")));
                productDayIncreasePO.setQunarModifiedAmt(new BigDecimal(requestMap.get("modifiedAmt")));
                break;
            case 4:
                productDayIncreasePO.setMeituanAdjustmentType(Integer.valueOf(requestMap.get("adjustmentType")));
                productDayIncreasePO.setMeituanModifiedAmt(new BigDecimal(requestMap.get("modifiedAmt")));
                break;
            case 5:
                productDayIncreasePO.setFeizhuAdjustmentType(Integer.valueOf(requestMap.get("adjustmentType")));
                productDayIncreasePO.setFeizhuModifiedAmt(new BigDecimal(requestMap.get("modifiedAmt")));
                break;
            case 6:
                productDayIncreasePO.setTcylAdjustmentType(Integer.valueOf(requestMap.get("adjustmentType")));
                productDayIncreasePO.setTcylModifiedAmt(new BigDecimal(requestMap.get("modifiedAmt")));
                break;
            default:
        }
        return productDayIncreasePO;
    }

    /**
     * 组装加幅总表数据
     *
     * @param batchSaleDTO
     * @return
     */
    private List<ProductIncreasePO> assemblyIncrease(BatchSaleDTO batchSaleDTO) {
        List<ProductIncreasePO> productIncreasePOList = new ArrayList<ProductIncreasePO>();
        batchSaleDTO.getItemList().forEach(batchSaleItemDTO -> {
            ProductIncreasePO productIncreasePO = new ProductIncreasePO();
            productIncreasePO.setProductId(batchSaleItemDTO.getProductId());
            productIncreasePO.setCompanyCode(batchSaleDTO.getCompanyCode());
            productIncreasePO.setChannelCode(batchSaleDTO.getChannelCode());
            productIncreasePO.setChannelId(ChannelEnum.getNoByKey(batchSaleDTO.getChannelCode()));
            productIncreasePO.setStartDate(batchSaleItemDTO.getStartDate());
            productIncreasePO.setEndDate(batchSaleItemDTO.getEndDate());
            productIncreasePO.setWeekStr(batchSaleItemDTO.getWeekList());
            productIncreasePO.setAdjustmentType(batchSaleItemDTO.getAdjustmentType());
            productIncreasePO.setModifiedAmt(batchSaleItemDTO.getModifiedAmt());
            productIncreasePO.setCreatedBy(batchSaleDTO.getModifiedBy());
            productIncreasePO.setCreatedDt(batchSaleDTO.getModifiedDt());
            productIncreasePOList.add(productIncreasePO);
        });
        return productIncreasePOList;
    }

    /**
     * 组装加幅总表数据(单天调整)
     *
     * @param requestMap
     * @return
     */
    private ProductIncreasePO assemblySingleIncrease(Map<String, String> requestMap) {
        ProductIncreasePO productIncreasePO = new ProductIncreasePO();
        productIncreasePO.setProductId(Integer.valueOf(requestMap.get("productId")));
        productIncreasePO.setCompanyCode(requestMap.get("companyCode"));
        productIncreasePO.setChannelCode(requestMap.get("channelCode"));
        productIncreasePO.setChannelId(ChannelEnum.getNoByKey(requestMap.get("channelCode")));
        productIncreasePO.setStartDate(requestMap.get("saleDate"));
        productIncreasePO.setEndDate(requestMap.get("saleDate"));
        productIncreasePO.setWeekStr(null);
        productIncreasePO.setAdjustmentType(Integer.valueOf(requestMap.get("adjustmentType")));
        productIncreasePO.setModifiedAmt(new BigDecimal(requestMap.get("modifiedAmt")));
        productIncreasePO.setCreatedBy(requestMap.get("modifiedBy"));
        productIncreasePO.setCreatedDt(requestMap.get("modifiedDt"));
        return productIncreasePO;
    }

    /**
     * 组装每日表加幅数据list
     *
     * @param batchSaleDTO
     * @return
     */
    private List<ProductDayIncreasePO> assemblyDayIncreaseList(BatchSaleDTO batchSaleDTO) {
        List<ProductDayIncreasePO> productDayIncreasePOList = new ArrayList<ProductDayIncreasePO>();
        for (BatchSaleItemDTO batchSaleItemDTO : batchSaleDTO.getItemList()) {
            Date startDate = null;
            Date endDate = null;
            //开始日期在3个月之后的不处理
            if (DateUtil.compare(DateUtil.stringToDate(batchSaleItemDTO.getStartDate()), DateUtil.getDate(DateUtil.getCurrentDate(), 3)) < 0) {
                startDate = DateUtil.stringToDate(batchSaleItemDTO.getStartDate());
                if (DateUtil.compare(DateUtil.stringToDate(batchSaleItemDTO.getEndDate()), DateUtil.getDate(DateUtil.getCurrentDate(), 3)) > 0) {
                    endDate = DateUtil.getDate(DateUtil.getCurrentDate(), 3);
                } else {
                    endDate = DateUtil.stringToDate(batchSaleItemDTO.getEndDate());
                }
                //获取日期Map
                List<Date> dateList = DateUtil.getDateInWeekList(startDate, endDate, batchSaleItemDTO.getWeekList());
                if (dateList.size() > 0) {
                    for (Date date : dateList) {
                        ProductDayIncreasePO productDayIncreasePO = new ProductDayIncreasePO();
                        productDayIncreasePO.setProductId(batchSaleItemDTO.getProductId());
                        productDayIncreasePO.setCompanyCode(batchSaleDTO.getCompanyCode());
                        productDayIncreasePO.setSaleDate(DateUtil.dateToString(date));
                        productDayIncreasePO.setModifiedBy(batchSaleDTO.getModifiedBy());
                        productDayIncreasePO.setModifiedDt(batchSaleDTO.getModifiedDt());
                        switch (ChannelEnum.getNoByKey(batchSaleDTO.getChannelCode())) {
                            case 0:
                                productDayIncreasePO.setB2bAdjustmentType(batchSaleItemDTO.getAdjustmentType());
                                productDayIncreasePO.setB2bModifiedAmt(batchSaleItemDTO.getModifiedAmt());
                                break;
                            case 1:
                                productDayIncreasePO.setB2cAdjustmentType(batchSaleItemDTO.getAdjustmentType());
                                productDayIncreasePO.setB2cModifiedAmt(batchSaleItemDTO.getModifiedAmt());
                                break;
                            case 2:
                                productDayIncreasePO.setCtripAdjustmentType(batchSaleItemDTO.getAdjustmentType());
                                productDayIncreasePO.setCtripModifiedAmt(batchSaleItemDTO.getModifiedAmt());
                                break;
                            case 3:
                                productDayIncreasePO.setQunarAdjustmentType(batchSaleItemDTO.getAdjustmentType());
                                productDayIncreasePO.setQunarModifiedAmt(batchSaleItemDTO.getModifiedAmt());
                                break;
                            case 4:
                                productDayIncreasePO.setMeituanAdjustmentType(batchSaleItemDTO.getAdjustmentType());
                                productDayIncreasePO.setMeituanModifiedAmt(batchSaleItemDTO.getModifiedAmt());
                                break;
                            case 5:
                                productDayIncreasePO.setFeizhuAdjustmentType(batchSaleItemDTO.getAdjustmentType());
                                productDayIncreasePO.setFeizhuModifiedAmt(batchSaleItemDTO.getModifiedAmt());
                                break;
                            case 6:
                                productDayIncreasePO.setTcylAdjustmentType(batchSaleItemDTO.getAdjustmentType());
                                productDayIncreasePO.setTcylModifiedAmt(batchSaleItemDTO.getModifiedAmt());
                                break;
                            default:
                        }
                        productDayIncreasePOList.add(productDayIncreasePO);
                    }
                }
            }
        }
        return productDayIncreasePOList;
    }

    @Override
    public Response queryOrderProductPrice(QueryProductRequestDTO queryProductRequestDTO) {
        Response response = new Response(1);
        ProductSalePriceDTO productSalePriceDTO = null;

        try {
            //获取日期列表
            Map<Date, Date> dateMap = DateUtil.getDateMap(DateUtil.stringToDate(queryProductRequestDTO.getStartDate()), DateUtil.stringToDate(queryProductRequestDTO.getEndDate()));
            //查询产品
            ProductDTO productDTO = productMapper.queryProduct(queryProductRequestDTO.getProductId());
            productSalePriceDTO = BeanUtil.transformBean(productDTO, ProductSalePriceDTO.class);
            //查询底价
            Example priceExample = new Example(ProductPricePO.class);
            Example.Criteria priceCriteria = priceExample.createCriteria();
            priceCriteria.andEqualTo("productId", queryProductRequestDTO.getProductId());
            priceCriteria.andBetween("saleDate", queryProductRequestDTO.getStartDate(), queryProductRequestDTO.getEndDate());
            List<ProductPricePO> pricePOList = productPriceMapper.selectByExample(priceExample);
            //查询配额和房态
            List<ProductQuotaPO> productQuotaPOList = null;
            if (null != productDTO && null != productDTO.getQuotaAccountId()) {
                Example quotaExample = new Example(ProductQuotaPO.class);
                Example.Criteria quotaCriteria = quotaExample.createCriteria();
                quotaCriteria.andEqualTo("quotaAccountId", productDTO.getQuotaAccountId());
                quotaCriteria.andBetween("saleDate", queryProductRequestDTO.getStartDate(), queryProductRequestDTO.getEndDate());
                productQuotaPOList = productQuotaMapper.selectByExample(quotaExample);
            }
            //查询加幅
            Example salePriceExample = new Example(ProductDayIncreasePO.class);
            Example.Criteria salePriceCriteria = salePriceExample.createCriteria();
            salePriceCriteria.andEqualTo("productId", queryProductRequestDTO.getProductId());
            salePriceCriteria.andEqualTo("companyCode", queryProductRequestDTO.getCompanyCode());
            salePriceCriteria.andBetween("saleDate", queryProductRequestDTO.getStartDate(), queryProductRequestDTO.getEndDate());
            List<ProductDayIncreasePO> productDayIncreasePOList = productDayIncreaseMapper.selectByExample(salePriceExample);
            //组装数据
            this.assemblyProductSalePrice(dateMap, productSalePriceDTO, pricePOList, productQuotaPOList, productDayIncreasePOList, queryProductRequestDTO.getChannelCode(), productDTO.getCurrency());
            response.setModel(productSalePriceDTO.getPriceList());
        } catch (Exception e) {
            log.error("queryOrderProductPrice-service error!", e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @Override
    public PageInfo<ProductSaleLogDTO> queryProductSaleLogList(Map<String, String> requestMap) {
        PageHelper.startPage(Integer.valueOf(requestMap.get("currentPage")), Integer.valueOf(requestMap.get("pageSize")));
        List<ProductSaleLogDTO> productSaleLogDTOS = productMapper.queryProductSaleLogList(requestMap);

        if (StringUtil.isValidString(requestMap.get("saleDate")) && StringUtil.isValidString(requestMap.get("operationType"))
                && requestMap.get("operationType").equals("1")) {
            //计算星期几
            String saleDate = requestMap.get("saleDate");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtil.stringToDate(saleDate));
            String week = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));

            //批量操作时候，可能会有适应星期几的操作，这里处理，若是不在该操作范围内移除
            Iterator<ProductSaleLogDTO> iterator = productSaleLogDTOS.iterator();
            while (iterator.hasNext()) {
                ProductSaleLogDTO productSaleLogDTO = iterator.next();
                if (productSaleLogDTO.getOperationWeek() != null && StringUtil.isValidString(productSaleLogDTO.getOperationWeek()) && !productSaleLogDTO.getOperationWeek().contains(week)) {
                    iterator.remove();
                }
            }
        }
        return new PageInfo<ProductSaleLogDTO>(productSaleLogDTOS);
    }
	
	@Override
    public Response resolveDayIncrease(String startDate,String endDate) {
        Response response = new Response(1);
        try {
            if (StringUtil.isValidString(startDate)
                    && StringUtil.isValidString(endDate)
                    && DateUtil.compare(DateUtil.stringToDate(startDate),DateUtil.stringToDate(endDate)) <= 0) {
                int resolveProductCount = 0;
                //循环每一天分解加幅数据
                List<Date> dateList = DateUtil.getDateList(DateUtil.stringToDate(startDate),DateUtil.stringToDate(endDate));
                Map<String,Object> paramMap;
                //按商家+产品Id分组
                Map<String,ProductIncreasePO> companyProductMap;
                //按商家+产品Id存放每一天加幅数据（用于多渠道分解）
                Map<String,ProductDayIncreasePO> productDayIncreasePOMap;
                for (Date saleDate : dateList) {
                    //查询日期的星期
                    int week = DateUtil.getWeekOfDate(saleDate) - 1;//0-6表示周一至周天
                    //查询每一天的加幅总表数据
                    paramMap = new HashMap<>();
                    paramMap.put("saleDate",saleDate);
                    paramMap.put("week",week);
                    List<ProductIncreasePO> productIncreasePOList = productIncreaseMapper.queryOneDayIncrease(paramMap);
                    if (!CollectionUtils.isEmpty(productIncreasePOList)) {
                        companyProductMap = new HashMap<>();
                        productDayIncreasePOMap = new HashMap<>();
                        for (ProductIncreasePO productIncreasePO : productIncreasePOList) {
                            companyProductMap.put(productIncreasePO.getCompanyCode() + "#" + productIncreasePO.getProductId(),productIncreasePO);
                        }
                        resolveProductCount += companyProductMap.size();
                        for (String companyProductId : companyProductMap.keySet()) {
                            ProductIncreasePO productIncreasePO = companyProductMap.get(companyProductId);
                            if (null == productDayIncreasePOMap.get(companyProductId)) {
                                ProductDayIncreasePO productDayIncreasePO = new ProductDayIncreasePO();
                                productDayIncreasePO.setProductId(productIncreasePO.getProductId());
                                productDayIncreasePO.setCompanyCode(productIncreasePO.getCompanyCode());
                                productDayIncreasePO.setSaleDate(DateUtil.dateToString(saleDate));
                                productDayIncreasePO.setCreatedBy(productIncreasePO.getCreatedBy());
                                productDayIncreasePO.setCreatedDt(DateUtil.getCurrentDateStr(1));
                                this.assembleDayIncrease(productDayIncreasePO,productIncreasePO);
                                productDayIncreasePOMap.put(companyProductId,productDayIncreasePO);
                            }else {
                                this.assembleDayIncrease(productDayIncreasePOMap.get(companyProductId),productIncreasePO);
                            }
                        }
                        //每日加幅数据入库
                        List<ProductDayIncreasePO> productDayIncreasePOList = new ArrayList<>(productDayIncreasePOMap.values());
                        int oneTimeCount = 1000;//每次入库1000条
                        for (int i = 0;i < productDayIncreasePOList.size();i += oneTimeCount) {
                            if (i + oneTimeCount > productDayIncreasePOList.size()) {
                                productDayIncreaseMapper.mergeProductDayIncrease(productDayIncreasePOList.subList(i,productDayIncreasePOList.size() - 1));
                            }else {
                                productDayIncreaseMapper.mergeProductDayIncrease(productDayIncreasePOList.subList(i,i+oneTimeCount));
                            }
                        }
                    }
                }
                response.setModel("总共分解" + resolveProductCount + "个产品;");
            }else {
                response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.INVALID_INPUTPARAM.errorCode, ErrorCodeEnum.INVALID_INPUTPARAM.errorDesc);
            }
        }catch (Exception e) {
            log.error("resolveDayIncrease-service error!", e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode, ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 将加幅总表加幅组装到每日加幅对象
     * @param productDayIncreasePO
     * @param productIncreasePO
     */
    private void assembleDayIncrease(ProductDayIncreasePO productDayIncreasePO,ProductIncreasePO productIncreasePO) {
        switch (productIncreasePO.getChannelId()) {
            case 0:
                productDayIncreasePO.setB2bAdjustmentType(productIncreasePO.getAdjustmentType());
                productDayIncreasePO.setB2bModifiedAmt(productIncreasePO.getModifiedAmt());
                break;
            case 1:
                productDayIncreasePO.setB2cAdjustmentType(productIncreasePO.getAdjustmentType());
                productDayIncreasePO.setB2cModifiedAmt(productIncreasePO.getModifiedAmt());
                break;
            case 2:
                productDayIncreasePO.setCtripAdjustmentType(productIncreasePO.getAdjustmentType());
                productDayIncreasePO.setCtripModifiedAmt(productIncreasePO.getModifiedAmt());
                break;
            case 3:
                productDayIncreasePO.setQunarAdjustmentType(productIncreasePO.getAdjustmentType());
                productDayIncreasePO.setQunarModifiedAmt(productIncreasePO.getModifiedAmt());
                break;
            case 4:
                productDayIncreasePO.setMeituanAdjustmentType(productIncreasePO.getAdjustmentType());
                productDayIncreasePO.setMeituanModifiedAmt(productIncreasePO.getModifiedAmt());
                break;
            case 5:
                productDayIncreasePO.setFeizhuAdjustmentType(productIncreasePO.getAdjustmentType());
                productDayIncreasePO.setFeizhuModifiedAmt(productIncreasePO.getModifiedAmt());
                break;
            case 6:
                productDayIncreasePO.setTcylAdjustmentType(productIncreasePO.getAdjustmentType());
                productDayIncreasePO.setTcylModifiedAmt(productIncreasePO.getModifiedAmt());
                break;
            default:
        }
    }

    /**
     * 过滤产品是否有变化
     * @param productSaleStatusList
     */
    private void handlerProductSaleStatus(List<ProductSaleStatusPO> productSaleStatusList) {
        if(CollectionUtils.isEmpty(productSaleStatusList)){
            return ;
        }

        List<String> incrementList = new ArrayList<String>();
        Iterator<ProductSaleStatusPO> iterator = productSaleStatusList.iterator();
        while(iterator.hasNext()){
            ProductSaleStatusPO productSaleStatusPO = iterator.next();
            String redisKey = StringUtil.concat(productSaleStatusPO.getCompanyCode(), "_", String.valueOf(productSaleStatusPO.getProductId()));
            ProductSaleStatusDTO productSaleStatusDTO = StringUtil.parseObject(redisUtil.hmget(RedisKey.productSaleStatusKey, redisKey), ProductSaleStatusDTO.class);
            Integer type = -1;
            String channelCode = null;

            if((productSaleStatusPO.getB2bSaleStatus() != null) && (productSaleStatusDTO == null || productSaleStatusDTO.getB2bSaleStatus() == null
             || ( !productSaleStatusDTO.getB2bSaleStatus().equals(productSaleStatusPO.getB2bSaleStatus())))){
                channelCode = ChannelEnum.B2B.key;
                type = IncrementTypeEnum.ON_SALE.no;
            }

            if(productSaleStatusPO.getB2cSaleStatus() != null && (productSaleStatusDTO == null || productSaleStatusDTO.getB2cSaleStatus() == null
                    || (!productSaleStatusDTO.getB2cSaleStatus().equals(productSaleStatusPO.getB2cSaleStatus())))){
                channelCode = ChannelEnum.B2C.key;
                type = IncrementTypeEnum.ON_SALE.no;
            }

            if(productSaleStatusPO.getCtripSaleStatus() != null && (productSaleStatusDTO == null || productSaleStatusDTO.getCtripSaleStatus() == null
                    || (!productSaleStatusDTO.getCtripSaleStatus().equals(productSaleStatusPO.getCtripSaleStatus())))){
                channelCode = ChannelEnum.CTRIP.key;
                type = IncrementTypeEnum.ON_SALE.no;
            }

            if(productSaleStatusPO.getFeizhuSaleStatus() != null && (productSaleStatusDTO == null || productSaleStatusDTO.getFeizhuSaleStatus() == null
                    || (!productSaleStatusDTO.getFeizhuSaleStatus().equals(productSaleStatusPO.getFeizhuSaleStatus())))){
                channelCode = ChannelEnum.FEIZHU.key;
                type = IncrementTypeEnum.ON_SALE.no;
            }

            if(productSaleStatusPO.getMeituanSaleStatus() != null && (productSaleStatusDTO == null || productSaleStatusDTO.getMeituanSaleStatus() == null
                    || (!productSaleStatusDTO.getMeituanSaleStatus().equals(productSaleStatusPO.getMeituanSaleStatus())))){
                channelCode = ChannelEnum.MEITUAN.key;
                type = IncrementTypeEnum.ON_SALE.no;
            }

            if(productSaleStatusPO.getTcylSaleStatus() != null &&  (productSaleStatusDTO == null || productSaleStatusDTO.getTcylSaleStatus() == null
                    || (!productSaleStatusDTO.getTcylSaleStatus().equals(productSaleStatusPO.getTcylSaleStatus())))){
                channelCode = ChannelEnum.TCYL.key;
                type = IncrementTypeEnum.ON_SALE.no;
            }

            if(productSaleStatusPO.getQunarSaleStatus() != null && (productSaleStatusDTO == null || productSaleStatusDTO.getQunarSaleStatus() == null
                    || ( !productSaleStatusDTO.getQunarSaleStatus().equals(productSaleStatusPO.getQunarSaleStatus())))){
                channelCode = ChannelEnum.QUNAR.key;
                type = IncrementTypeEnum.ON_SALE.no;
            }

            if(type != -1){
                IncrementDTO incrementDTO = new IncrementDTO();
                incrementDTO.setProductId(productSaleStatusPO.getProductId());
                incrementDTO.setType(type);
                incrementDTO.setChannelCode(channelCode);
                incrementDTO.setCompanyCode(productSaleStatusPO.getCompanyCode());
                incrementList.add(JSON.toJSONString(incrementDTO));
            }else {
                iterator.remove();
            }
        }
        if(CollectionUtils.isNotEmpty(incrementList)) {
            redisUtil.rpush(RedisKey.incrementKey, incrementList);
        }
     }

    /**
     * 处理产品加幅
     * @param productDayIncreasePOList
     */
    private void handlerProductIncrease(List<ProductDayIncreasePO> productDayIncreasePOList) {
        if (CollectionUtils.isEmpty(productDayIncreasePOList)) {
            return;
        }

        Map<String, IncrementDTO> incrementMap = new HashMap<String, IncrementDTO>();
        Iterator<ProductDayIncreasePO> iterator = productDayIncreasePOList.iterator();
        while (iterator.hasNext()) {
            ProductDayIncreasePO productDayIncreasePO = iterator.next();
            String redisKey = StringUtil.concat(productDayIncreasePO.getCompanyCode(), "_", String.valueOf(productDayIncreasePO.getProductId()), "_", productDayIncreasePO.getSaleDate());
            ProductSaleIncreaseDTO productSaleIncreaseDTO = StringUtil.parseObject(redisUtil.hmget(RedisKey.productSalePriceKey, redisKey), ProductSaleIncreaseDTO.class);

            Integer type = -1;
            String channelCode = null;

            if ((productDayIncreasePO.getB2bAdjustmentType() != null && productDayIncreasePO.getB2bModifiedAmt() != null) &&
                    (productSaleIncreaseDTO == null || productSaleIncreaseDTO.getB2bAdjustmentType() == null || productSaleIncreaseDTO.getB2bModifiedAmt() == null
                    || (!productDayIncreasePO.getB2bModifiedAmt().equals(productSaleIncreaseDTO.getB2bModifiedAmt()) && !productDayIncreasePO.getB2bAdjustmentType().equals(productSaleIncreaseDTO.getB2bAdjustmentType())))) {
                type = IncrementTypeEnum.SALE_PRICE.no;
                channelCode = ChannelEnum.B2B.key;
            }

            if ((productDayIncreasePO.getB2cAdjustmentType() != null && productDayIncreasePO.getB2cModifiedAmt() != null) && (productSaleIncreaseDTO == null || productSaleIncreaseDTO.getB2cAdjustmentType() == null || productSaleIncreaseDTO.getB2cModifiedAmt() == null
                    || (!productDayIncreasePO.getB2cAdjustmentType().equals(productSaleIncreaseDTO.getB2cAdjustmentType()) && !productDayIncreasePO.getB2cModifiedAmt().equals(productSaleIncreaseDTO.getB2cModifiedAmt())))) {
                type = IncrementTypeEnum.SALE_PRICE.no;
                channelCode = ChannelEnum.B2C.key;
            }

            if ((productDayIncreasePO.getCtripAdjustmentType() != null && productDayIncreasePO.getCtripModifiedAmt() != null) &&
                    (productSaleIncreaseDTO == null || productSaleIncreaseDTO.getCtripAdjustmentType() == null || productSaleIncreaseDTO.getCtripModifiedAmt() == null
                    || (!productDayIncreasePO.getCtripAdjustmentType().equals(productSaleIncreaseDTO.getCtripAdjustmentType()) && !productDayIncreasePO.getCtripModifiedAmt().equals(productSaleIncreaseDTO.getCtripModifiedAmt())))) {
                type = IncrementTypeEnum.SALE_PRICE.no;
                channelCode = ChannelEnum.CTRIP.key;
            }

            if ((productDayIncreasePO.getMeituanAdjustmentType() != null && productDayIncreasePO.getMeituanModifiedAmt() != null)
                  && (productSaleIncreaseDTO == null || productSaleIncreaseDTO.getMeituanAdjustmentType() == null || productSaleIncreaseDTO.getMeituanModifiedAmt() == null
                    || (!productDayIncreasePO.getMeituanAdjustmentType().equals(productSaleIncreaseDTO.getMeituanAdjustmentType()) && !productDayIncreasePO.getMeituanModifiedAmt().equals(productSaleIncreaseDTO.getMeituanModifiedAmt())))) {
                type = IncrementTypeEnum.SALE_PRICE.no;
                channelCode = ChannelEnum.MEITUAN.key;
            }

            if ((productDayIncreasePO.getFeizhuAdjustmentType() != null && productDayIncreasePO.getFeizhuModifiedAmt() != null)
                   && (productSaleIncreaseDTO == null || productSaleIncreaseDTO.getFeizhuAdjustmentType() == null || productSaleIncreaseDTO.getFeizhuModifiedAmt() == null
                    || (!productDayIncreasePO.getFeizhuAdjustmentType().equals(productSaleIncreaseDTO.getFeizhuAdjustmentType()) && !productDayIncreasePO.getFeizhuModifiedAmt().equals(productSaleIncreaseDTO.getFeizhuModifiedAmt())))) {
                type = IncrementTypeEnum.SALE_PRICE.no;
                channelCode = ChannelEnum.FEIZHU.key;
            }

            if ((productDayIncreasePO.getQunarAdjustmentType() != null && productDayIncreasePO.getQunarModifiedAmt() != null)
                   && (productSaleIncreaseDTO == null || productSaleIncreaseDTO.getQunarAdjustmentType() == null || productSaleIncreaseDTO.getQunarModifiedAmt() == null
                    || (!productDayIncreasePO.getQunarAdjustmentType().equals(productSaleIncreaseDTO.getQunarAdjustmentType()) && !productDayIncreasePO.getQunarModifiedAmt().equals(productSaleIncreaseDTO.getQunarModifiedAmt())))) {
                type = IncrementTypeEnum.SALE_PRICE.no;
                channelCode = ChannelEnum.QUNAR.key;
            }

            if ((productDayIncreasePO.getTcylAdjustmentType() != null && productDayIncreasePO.getTcylModifiedAmt() != null) && (productSaleIncreaseDTO == null || productSaleIncreaseDTO.getTcylAdjustmentType() == null || productSaleIncreaseDTO.getTcylModifiedAmt() == null
                    || (!productDayIncreasePO.getTcylAdjustmentType().equals(productSaleIncreaseDTO.getTcylAdjustmentType()) && !productDayIncreasePO.getTcylModifiedAmt().equals(productSaleIncreaseDTO.getTcylModifiedAmt())))) {
                type = IncrementTypeEnum.SALE_PRICE.no;
                channelCode = ChannelEnum.TCYL.key;
            }

            if (type != -1){
                if(incrementMap != null && incrementMap.size() > 0 && incrementMap.get(StringUtil.concat(productDayIncreasePO.getCompanyCode(), "_", String.valueOf(productDayIncreasePO.getProductId()))) != null){
                    incrementMap.get(StringUtil.concat(productDayIncreasePO.getCompanyCode(), "_", String.valueOf(productDayIncreasePO.getProductId()))).getSaleDate().add(productDayIncreasePO.getSaleDate());
                }else {
                    IncrementDTO incrementDTO = new IncrementDTO();
                    incrementDTO.setProductId(productDayIncreasePO.getProductId());
                    incrementDTO.setCompanyCode(productDayIncreasePO.getCompanyCode());
                    incrementDTO.setChannelCode(channelCode);
                    incrementDTO.setType(type);
                    incrementDTO.setSaleDate(new ArrayList<String>(Arrays.asList(productDayIncreasePO.getSaleDate())));
                    incrementMap.put(StringUtil.concat(productDayIncreasePO.getCompanyCode(), "_", String.valueOf(productDayIncreasePO.getProductId())), incrementDTO);
                }
            }else {
                iterator.remove();
            }
        }

        if(incrementMap != null  && incrementMap.size() > 0) {
            List<String> incrementList = incrementMap.values().stream().map(i -> JSON.toJSONString(i)).collect(Collectors.toList());
            incrementMap = null;
            redisUtil.rpush(RedisKey.incrementKey, incrementList);
        }

    }
}
