package com.mgs.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.dis.dto.IncrementDTO;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.dis.dto.ProductBasePriceAndRoomStatusDTO;
import com.mgs.dis.dto.ProductRestrictDTO;
import com.mgs.dis.remote.InitRemote;
import com.mgs.enums.AdjustmentTypeEnum;
import com.mgs.enums.ChannelEnum;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.IncrementTypeEnum;
import com.mgs.enums.OverDraftStatusEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.enums.RoomStatusEnum;
import com.mgs.hotel.dto.BasicRoomInfoDTO;
import com.mgs.hotel.remote.HotelRemote;
import com.mgs.keys.RedisKey;
import com.mgs.product.domain.*;
import com.mgs.product.dto.*;
import com.mgs.product.mapper.*;
import com.mgs.product.service.ProductService;
import com.mgs.util.BeanUtil;
import com.mgs.util.DateUtil;
import com.mgs.util.RedisUtil;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.mgs.util.DateUtil.hour_format;

/**
 * @Auther: Owen
 * @Date: 2019/4/24 11:21
 * @Description: 产品service实现类
 */
@Slf4j
@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private QuotaAccountMapper quotaAccountMapper;

    @Autowired
    private ProductSaleStatusMapper productSaleStatusMapper;

    @Autowired
    private ProductRestrictMapper productRestrictMapper;

    @Autowired
    private CompanyChannelMapper companyChannelMapper;

    @Autowired
    private ProductPriceMapper productPriceMapper;

    @Autowired
    private ProductQuotaMapper productQuotaMapper;

    @Autowired
    private HotelRemote hotelRemote;

    @Autowired
    private ProductLogMapper productLogMapper;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private InitRemote initRemote;

    @Override
    @Transactional
    public Response addProduct(ProductDTO productDTO) {
        Response response = null;
        try {
            //添加配额账号
            QuotaAccountPO quotaAccountPO = this.assemblyQuotaAccount(productDTO);
            quotaAccountMapper.insert(quotaAccountPO);

            //添加产品
            ProductPO productPO = BeanUtil.transformBean(productDTO,ProductPO.class);
            productPO.setQuotaAccountId(quotaAccountPO.getQuotaAccountId());
            productPO.setActive(1);
            productMapper.insert(productPO);

            //添加条款
            ProductRestrictPO productRestrictPO = BeanUtil.transformBean(productDTO,ProductRestrictPO.class);
            productRestrictPO.setProductId(productPO.getProductId());
            productRestrictMapper.insert(productRestrictPO);

            //添加产品和运营商管理表（渠道上架状态）
            ProductSaleStatusPO productSaleStatusPO = new ProductSaleStatusPO();
            productSaleStatusPO.setActive(1);
            productSaleStatusPO.setProductId(productPO.getProductId());
            productSaleStatusPO.setCompanyCode(productDTO.getCompanyCode());
            productSaleStatusPO.setB2bSaleStatus(0);
            productSaleStatusPO.setB2cSaleStatus(0);
            productSaleStatusPO.setCtripSaleStatus(0);
            productSaleStatusPO.setQunarSaleStatus(0);
            productSaleStatusPO.setFeizhuSaleStatus(0);
            productSaleStatusPO.setMeituanSaleStatus(0);
            productSaleStatusPO.setTcylSaleStatus(0);
            productSaleStatusPO.setCreatedBy(productDTO.getCreatedBy());
            productSaleStatusPO.setCreatedDt(productDTO.getCreatedDt());
            productSaleStatusMapper.insert(productSaleStatusPO);

            initRemote.initRestrict(productDTO);

            response = new Response(1);
            response.setModel(productPO.getProductId());
        }catch (Exception e) {
            log.error("addProduct-service error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @Override
    @Transactional
    public Response modifyProduct(ProductDTO productDTO) {
        Response response = null;
        try {
            //修改产品
            ProductPO productPO = BeanUtil.transformBean(productDTO,ProductPO.class);
            productMapper.updateByPrimaryKeySelective(productPO);

            IncrementDTO incrementDTO = handlerBreakfast(productDTO);

            //修改条款
            ProductRestrictPO productRestrictPO = BeanUtil.transformBean(productDTO,ProductRestrictPO.class);
            //过滤条款修改
            handlerProductRestrict(productRestrictPO, incrementDTO);

            if(productRestrictPO != null) {
                Example productRestrictExample = new Example(ProductRestrictPO.class);
                Example.Criteria productRestrictCriteria = productRestrictExample.createCriteria();
                productRestrictCriteria.andEqualTo("productId", productDTO.getProductId());
                productRestrictMapper.updateByExampleSelective(productRestrictPO, productRestrictExample);

            }

            if(incrementDTO != null){
                initRemote.initRestrict(productDTO);
            }

            response = new Response(1);
            response.setModel(productPO.getProductId());
        }catch (Exception e) {
            log.error("modifyProduct-service error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }


    @Override
    @Transactional
    public Response deleteProduct(Map<String,String> requestMap) {
        Response response = new Response(1);
        DeleteProductResponseDTO deleteProductResponseDTO = new DeleteProductResponseDTO();
        try {
            //查询产品上架状态
            ProductSaleStatusPO productSaleStatusPO = new ProductSaleStatusPO();
            productSaleStatusPO.setProductId(Integer.valueOf(requestMap.get("productId")));
            productSaleStatusPO.setCompanyCode(requestMap.get("companyCode"));
            productSaleStatusPO.setActive(1);
            ProductSaleStatusPO productSaleStatusPO1 = productSaleStatusMapper.selectOne(productSaleStatusPO);
            if (this.checkSaleStatus(productSaleStatusPO1)) {//有渠道上架，返回不能删除
                deleteProductResponseDTO.setStatus(0);
                deleteProductResponseDTO.setReason(ErrorCodeEnum.PRODUCTONSALEWITHNODELETE.errorDesc);
            }else {//无渠道上架，将此运营商产品设置为无效
                productSaleStatusPO.setActive(0);
                productSaleStatusPO.setId(productSaleStatusPO1.getId());
                productSaleStatusPO.setModifiedBy(requestMap.get("modifiedBy"));
                productSaleStatusPO.setModifiedDt(requestMap.get("modifiedDt"));
                productSaleStatusMapper.updateByPrimaryKeySelective(productSaleStatusPO);
                deleteProductResponseDTO.setStatus(1);
            }
            response.setModel(deleteProductResponseDTO);
        }catch (Exception e) {
            log.error("deleteProduct-service error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @Override
    public Response queryProduct(Map<String,String> requestMap) {
        Response response = new Response(1);
        try {
            //查询产品
            ProductDTO productDTO = productMapper.queryProduct(Integer.valueOf(requestMap.get("productId")));

            //查询条款
            ProductRestrictPO productRestrictPO = new ProductRestrictPO();
            productRestrictPO.setProductId(Integer.valueOf(requestMap.get("productId")));
            ProductRestrictPO productRestrictPO1 = productRestrictMapper.selectOne(productRestrictPO);
            BeanUtils.copyProperties(productDTO,productRestrictPO1);//复制条款

            response.setModel(productDTO);
        }catch (Exception e) {
            log.error("queryProduct-service error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @Override
    public Response queryHotelList(QueryProductRequestDTO queryProductRequestDTO) {
        Response response = new Response(1);
        PaginationSupportDTO<ProductHotelDTO> paginationSupportDTO = new PaginationSupportDTO<ProductHotelDTO>();

        try {
            PageHelper.startPage(queryProductRequestDTO.getCurrentPage(),queryProductRequestDTO.getPageSize());
            List<ProductHotelDTO> productHotelDTOList = productMapper.queryHotelList(queryProductRequestDTO);
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
            List<ProductTempDTO> productTempDTOList = productMapper.queryHotelProducts(queryProductRequestDTO);
                //查询运营商对应渠道
                Example companyChannelExample = new Example(CompanyChannelPO.class);
                Example.Criteria companyChannelCriteria = companyChannelExample.createCriteria();
                companyChannelCriteria.andEqualTo("companyCode",queryProductRequestDTO.getCompanyCode());
                List<CompanyChannelPO> companyChannelPOList = companyChannelMapper.selectByExample(companyChannelExample);
                //组装酒店产品数据
                hotelProductsDTO = this.assemblyHotelProducts(productTempDTOList,companyChannelPOList,queryProductRequestDTO.getHotelId());
            response.setModel(hotelProductsDTO);
        }catch (Exception e) {
            log.error("queryHotelProducts-service error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @Override
    @Transactional
    public Response dailyModifyProductInfo(ProductDayQuotationDTO productDayQuotationDTO) {
        Response response = new Response(1);
        try {
            List<ProductDayQuotationDTO> productDayQuotationDTOList = new ArrayList<ProductDayQuotationDTO>();
            //设置配额账号Id
            ProductPO productPO = new ProductPO();
            productPO.setProductId(productDayQuotationDTO.getProductId());
            ProductPO productPO1 = productMapper.selectByPrimaryKey(productPO);
           // Boolean subtractFlag = false;
            if (null != productPO1.getQuotaAccountId()) {
                //调整方式为减少时，配额改为负数
                if (productDayQuotationDTO.getQuotaAdjustmentType().equals(1)) {
                    //subtractFlag = true;
                    productDayQuotationDTO.setModifiedQuota(0-productDayQuotationDTO.getModifiedQuota());
                }
                productDayQuotationDTO.setQuotaAccountId(productPO1.getQuotaAccountId());
            }
            if(productDayQuotationDTO.getBasePriceAdjustmentType() != null && productDayQuotationDTO.getModifiedBasePrice() != null){
                if (productDayQuotationDTO.getBasePriceAdjustmentType().equals(1)) {
                    //subtractFlag = true;
                    productDayQuotationDTO.setModifiedBasePrice(BigDecimal.ZERO.subtract(productDayQuotationDTO.getModifiedBasePrice()));
                }
            }
            productDayQuotationDTOList.add(productDayQuotationDTO);

            //TODO 对比redis里面的数据，进行一次过滤
            filterModifyData(productDayQuotationDTOList);

            if(CollectionUtils.isNotEmpty(productDayQuotationDTOList)){
                //修改价格，传入价格时才修改价格
                if (null != productDayQuotationDTO.getModifiedBasePrice() && productDayQuotationDTO.getBasePriceAdjustmentType() != null){
                    switch (productDayQuotationDTO.getBasePriceAdjustmentType()) {
                        case 0:
                            productPriceMapper.batchModifyBasePriceAdd(productDayQuotationDTOList);
                            break;
                        case 1:
                            productPriceMapper.batchModifyBasePriceSubtract(productDayQuotationDTOList);
                            /*if (subtractFlag) {//如果是减少配额，需要将负数配额设置为0
                                List<Integer> quotaAccountIdList = new ArrayList<Integer>();
                                quotaAccountIdList.add(productPO1.getQuotaAccountId());
                                productQuotaMapper.updateQuotaLessThanZero(quotaAccountIdList);
                                productQuotaMapper.updateRemainingQuotaLessThanZero(quotaAccountIdList);
                            }*/
                            break;
                        case 2:
                            productPriceMapper.batchModifyBasePriceEquals(productDayQuotationDTOList);
                            break;
                        default:
                    }
                    //productPriceMapper.batchModifyBasePrice(productDayQuotationDTOList);
                }
                //修改房态配额
                if (null != productDayQuotationDTO.getQuotaAccountId() && null != productDayQuotationDTO.getQuotaAdjustmentType()) {
                    //房态为不变时，传-1，设置为null
                    if (null != productDayQuotationDTO.getRoomStatus() && productDayQuotationDTO.getRoomStatus() < 0) {
                        productDayQuotationDTO.setRoomStatus(null);
                    }
                    //是否可超为不变时，传-1，设置为null
                    if (null != productDayQuotationDTO.getOverDraftStatus() && productDayQuotationDTO.getOverDraftStatus() < 0) {
                        productDayQuotationDTO.setOverDraftStatus(null);
                    }
                    switch (productDayQuotationDTO.getQuotaAdjustmentType()) {
                        case 0:
                            productQuotaMapper.batchModifyQuotaAdd(productDayQuotationDTOList);
                            break;
                        case 1:
                            productQuotaMapper.batchModifyQuotaSubtract(productDayQuotationDTOList);
                            /*if (subtractFlag) {//如果是减少配额，需要将负数配额设置为0
                                List<Integer> quotaAccountIdList = new ArrayList<Integer>();
                                quotaAccountIdList.add(productPO1.getQuotaAccountId());
                                productQuotaMapper.updateQuotaLessThanZero(quotaAccountIdList);
                                productQuotaMapper.updateRemainingQuotaLessThanZero(quotaAccountIdList);
                            }*/
                            break;
                        case 2:
                            productQuotaMapper.batchModifyQuotaEquals(productDayQuotationDTOList);
                            break;
                        default:
                    }

                    ProductLogPO productLogPO = new ProductLogPO();
                    productLogPO.setProductId(productDayQuotationDTO.getProductId());
                    productLogPO.setStartDate(productDayQuotationDTO.getSaleDate());
                    productLogPO.setEndDate(productDayQuotationDTO.getSaleDate());
                    productLogPO.setCreatedBy(productDayQuotationDTO.getModifiedBy());
                    productLogPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
                    String content = (productDayQuotationDTO.getBasePriceAdjustmentType() == null || productDayQuotationDTO.getModifiedBasePrice() == null?"":"将底价修"+ AdjustmentTypeEnum.getDesc(productDayQuotationDTO.getBasePriceAdjustmentType()) + productDayQuotationDTO.getModifiedBasePrice() +",")
                            + (productDayQuotationDTO.getRoomStatus() == null?"":"将关房设置修改为"+ RoomStatusEnum.getDesc(productDayQuotationDTO.getRoomStatus()) + ",")
                            + (productDayQuotationDTO.getOverDraftStatus() == null?"":"将售罄设置修改为"+ OverDraftStatusEnum.getDesc(productDayQuotationDTO.getOverDraftStatus()) + ",")
                            + (productDayQuotationDTO.getQuotaAdjustmentType() == null || productDayQuotationDTO.getModifiedQuota() == null?"":"将可售数量"+ AdjustmentTypeEnum.getDesc(productDayQuotationDTO.getQuotaAdjustmentType())+ productDayQuotationDTO.getModifiedQuota() + ",");
                    productLogPO.setContent(content.substring(0, content.length() - 1 ));
                    productLogMapper.insert(productLogPO);
                }

                initRemote.initBasePriceAndRoomStatus(productDayQuotationDTOList);
            }
        }catch (Exception e) {
            log.error("dailyModifyProductInfo-service error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @Override
    @Transactional
    public Response batchModifyBasePrice(List<BatchQuotationDTO> batchQuotationDTOList) {
        Response response = new Response(1);
        try {
            //组装每日价格
            List<ProductDayQuotationDTO> productDayQuotationDTOList = this.assemblyProductDayQuotation(batchQuotationDTOList,1);

            //TODO 对比redis里面的数据，进行一次过滤
            filterModifyData(productDayQuotationDTOList);

            List<ProductDayQuotationDTO> productDayQuotationAdd = new ArrayList<ProductDayQuotationDTO>();
            List<ProductDayQuotationDTO> productDayQuotationSubstract = new ArrayList<ProductDayQuotationDTO>();
            List<ProductDayQuotationDTO> productDayQuotationEquals = new ArrayList<ProductDayQuotationDTO>();
            productDayQuotationDTOList.forEach(t ->{
                if(t.getBasePriceAdjustmentType() != null){
                    switch (t.getBasePriceAdjustmentType()) {
                        case 0:
                            productDayQuotationAdd.add(t);
                            break;
                        case 1:
                            productDayQuotationSubstract.add(t);
                            /*if (subtractFlag) {//如果是减少配额，需要将负数配额设置为0
                                List<Integer> quotaAccountIdList = new ArrayList<Integer>();
                                quotaAccountIdList.add(productPO1.getQuotaAccountId());
                                productQuotaMapper.updateQuotaLessThanZero(quotaAccountIdList);
                                productQuotaMapper.updateRemainingQuotaLessThanZero(quotaAccountIdList);
                            }*/
                            break;
                        case 2:
                            productDayQuotationEquals.add(t);
                            break;
                        default:
                    }
                }
            });

            if(CollectionUtils.isNotEmpty(productDayQuotationAdd)){
                productPriceMapper.batchModifyBasePriceAdd(productDayQuotationDTOList);
            }

            if(CollectionUtils.isNotEmpty(productDayQuotationSubstract)){
                productPriceMapper.batchModifyBasePriceSubtract(productDayQuotationDTOList);
            }

            if(CollectionUtils.isNotEmpty(productDayQuotationEquals)){
                productPriceMapper.batchModifyBasePriceEquals(productDayQuotationDTOList);
            }


            List<ProductLogPO> productLogPOList = new ArrayList<ProductLogPO>();
            for(BatchQuotationDTO batchQuotationDTO: batchQuotationDTOList){
                ProductLogPO productLogPO = new ProductLogPO();
                productLogPO.setProductId(batchQuotationDTO.getProductId());
                productLogPO.setStartDate(batchQuotationDTO.getStartDate());
                productLogPO.setEndDate(batchQuotationDTO.getEndDate());
                productLogPO.setCreatedBy(batchQuotationDTO.getModifiedBy());
                productLogPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
                productLogPO.setOperationWeek(batchQuotationDTO.getWeekList());

                String content = (batchQuotationDTO.getBasePriceAdjustmentType() == null || batchQuotationDTO.getModifiedBasePrice() == null?"":"将底价"+ AdjustmentTypeEnum.getDesc(batchQuotationDTO.getBasePriceAdjustmentType())+ batchQuotationDTO.getModifiedBasePrice() + ",")
                        + (batchQuotationDTO.getRoomStatus() == null?"":"将关房设置修改为"+ RoomStatusEnum.getDesc(batchQuotationDTO.getRoomStatus()) + ",")
                        + (batchQuotationDTO.getOverDraftStatus() == null?"":"将售罄设置修改为"+ OverDraftStatusEnum.getDesc(batchQuotationDTO.getOverDraftStatus()) + ",")
                        + (batchQuotationDTO.getQuotaAdjustmentType() == null || batchQuotationDTO.getModifiedQuota() == null?"":"将可售数量"+ AdjustmentTypeEnum.getDesc(batchQuotationDTO.getQuotaAdjustmentType())+ batchQuotationDTO.getModifiedQuota() + ",");
                productLogPO.setContent(content.substring(0, content.length() - 1));
                productLogPOList.add(productLogPO);
            }

            productLogMapper.insertList(productLogPOList);

            //TODO 将redis对应的数据同步一遍
            if(CollectionUtils.isNotEmpty(productDayQuotationDTOList)) {
                initRemote.initBasePriceAndRoomStatus(productDayQuotationDTOList);
            }
        }catch (Exception e) {
            log.error("batchModifyBasePrice-service error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    @Override
    @Transactional
    public Response batchModifyRoomStatus(List<BatchQuotationDTO> batchQuotationDTOList) {
        Response response = new Response(1);
        try {
            //组装每日房态
            List<ProductDayQuotationDTO> productDayQuotationDTOList = this.assemblyProductDayQuotation(batchQuotationDTOList,2);
            //批量修改房态配额
            //拆分成三部分更新：增加、减少，等于
            List<ProductDayQuotationDTO> productDayQuotationDTOListAdd = new ArrayList<ProductDayQuotationDTO>();
            List<ProductDayQuotationDTO> productDayQuotationDTOListSubtract = new ArrayList<ProductDayQuotationDTO>();
            List<ProductDayQuotationDTO> productDayQuotationDTOListEquals = new ArrayList<ProductDayQuotationDTO>();
            Map<Integer,Integer> quotaAccountIdMap = new HashMap<Integer,Integer>();//配额账号Id，用于减少配额时更新配额小于0的数据
            for (ProductDayQuotationDTO productDayQuotationDTO : productDayQuotationDTOList) {
                if (null != productDayQuotationDTO.getQuotaAdjustmentType()) {
                    switch (productDayQuotationDTO.getQuotaAdjustmentType()) {
                        case 0:
                            productDayQuotationDTOListAdd.add(productDayQuotationDTO);
                            break;
                        case 1:
                            productDayQuotationDTOListSubtract.add(productDayQuotationDTO);
                            quotaAccountIdMap.put(productDayQuotationDTO.getQuotaAccountId(),productDayQuotationDTO.getQuotaAccountId());
                            break;
                        case 2:
                            productDayQuotationDTOListEquals.add(productDayQuotationDTO);
                            break;
                        default:
                    }
                }
            }

            //TODO 对比redis里面的数据进行比较
            filterModifyData(productDayQuotationDTOList);

            if (!CollectionUtils.isEmpty(productDayQuotationDTOListAdd)) {
                productQuotaMapper.batchModifyQuotaAdd(productDayQuotationDTOListAdd);
            }
            if (!CollectionUtils.isEmpty(productDayQuotationDTOListSubtract)) {
                productQuotaMapper.batchModifyQuotaSubtract(productDayQuotationDTOListSubtract);
               /* if (null != quotaAccountIdMap) {
                    List<Integer> quotaAccountIdList = new ArrayList<Integer>();
                    for (Integer quotaAccountId : quotaAccountIdMap.keySet()) {
                        quotaAccountIdList.add(quotaAccountId);
                    }
                    productQuotaMapper.updateRemainingQuotaLessThanZero(quotaAccountIdList);
                    productQuotaMapper.updateQuotaLessThanZero(quotaAccountIdList);
                }*/

            }
            if (!CollectionUtils.isEmpty(productDayQuotationDTOListEquals)) {
                productQuotaMapper.batchModifyQuotaEquals(productDayQuotationDTOListEquals);
            }

            List<ProductLogPO> productLogPOList = new ArrayList<ProductLogPO>();
            for(BatchQuotationDTO batchQuotationDTO: batchQuotationDTOList){
                ProductLogPO productLogPO = new ProductLogPO();
                productLogPO.setProductId(batchQuotationDTO.getProductId());
                productLogPO.setStartDate(batchQuotationDTO.getStartDate());
                productLogPO.setEndDate(batchQuotationDTO.getEndDate());
                productLogPO.setCreatedBy(batchQuotationDTO.getModifiedBy());
                productLogPO.setCreatedDt(DateUtil.dateToString(new Date(), hour_format));
                productLogPO.setOperationWeek(batchQuotationDTO.getWeekList());

                String content = (batchQuotationDTO.getBasePriceAdjustmentType() == null || batchQuotationDTO.getModifiedBasePrice() == null?"":"将底价"+ AdjustmentTypeEnum.getDesc(batchQuotationDTO.getBasePriceAdjustmentType())+ batchQuotationDTO.getModifiedBasePrice() + ",")
                        + (batchQuotationDTO.getRoomStatus() == null?"":"将关房设置修改为"+ RoomStatusEnum.getDesc(batchQuotationDTO.getRoomStatus()) + ",")
                        + (batchQuotationDTO.getOverDraftStatus() == null?"":"将售罄设置修改为"+ OverDraftStatusEnum.getDesc(batchQuotationDTO.getOverDraftStatus()) + ",")
                        + (batchQuotationDTO.getQuotaAdjustmentType() == null || batchQuotationDTO.getModifiedQuota() == null?"":"将可售数量"+ AdjustmentTypeEnum.getDesc(batchQuotationDTO.getQuotaAdjustmentType())+ batchQuotationDTO.getModifiedQuota() + ",");
                productLogPO.setContent(content.substring(0, content.length() - 1));
                productLogPOList.add(productLogPO);
            }

            productLogMapper.insertList(productLogPOList);

            //TODO 将redis对应的数据同步一遍
            if(CollectionUtils.isNotEmpty(productDayQuotationDTOList)){
                initRemote.initBasePriceAndRoomStatus(productDayQuotationDTOList);
            }

        }catch (Exception e) {
            log.error("batchModifyRoomStatus-service error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code,ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 查询产品列表供订单使用
     * @param request
     * @return
     */
    @Override
    public PaginationSupportDTO<ProductOrderQueryDTO> queryOrderProductList(ProductOrderQueryRequestDTO request) {
         log.info("queryOrderProductList param: {}"+ JSON.toJSONString(request));
         Response response = new Response();
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        QueryProductRequestDTO queryProductRequestDTO = new QueryProductRequestDTO();
        List<Integer> hotelId = new ArrayList<>();
        hotelId.add(request.getHotelId());
        queryProductRequestDTO.setCompanyCode(request.getCompanyCode());
        queryProductRequestDTO.setHotelIdList(hotelId);
        queryProductRequestDTO.setRoomId(request.getRoomId());
        queryProductRequestDTO.setSupplierCode(request.getSupplierCode());
        List<ProductTempDTO> productTempDTOS = productMapper.querySaleProducts(queryProductRequestDTO);
        List<ProductOrderQueryDTO> productOrderQueryDTO = new ArrayList<ProductOrderQueryDTO>();
        Map<Integer, BigDecimal> totalMap = new HashMap<>();
        if (request.getStartDate() != null) {
            List<TotalAmtDTO> totalAmtDTOS = productMapper.queryTotalAmt(request);
            for (TotalAmtDTO totalAmt : totalAmtDTOS) {
                if (null != totalMap.get(totalAmt.getProductId())) {
                    totalMap.put(totalAmt.getProductId(), totalMap.get(totalAmt.getProductId()).add(totalAmt.getBasePrice()));
                }else {
                    totalMap.put(totalAmt.getProductId(),totalAmt.getBasePrice());
                }
            }
        }
        for (ProductTempDTO productTempDTOS1 : productTempDTOS) {

               if(request.getStartDate() != null && (null == totalMap.get(productTempDTOS1.getProductId())
                       || totalMap.get(productTempDTOS1.getProductId()).compareTo(BigDecimal.ZERO)==0)){
                   continue;
                }
                ProductOrderQueryDTO productOrderQueryDTO1 = new ProductOrderQueryDTO();
                productOrderQueryDTO1.setProductId(productTempDTOS1.getProductId());
                productOrderQueryDTO1.setProductName(productTempDTOS1.getProductName());
                productOrderQueryDTO1.setRoomId(productTempDTOS1.getRoomId());
                productOrderQueryDTO1.setRoomName(productTempDTOS1.getRoomName());
                productOrderQueryDTO1.setBedTypes(productTempDTOS1.getBedTypes());
                productOrderQueryDTO1.setPurchaseType(productTempDTOS1.getPurchaseType());
                productOrderQueryDTO1.setSupplierCode(productTempDTOS1.getSupplierCode());
                productOrderQueryDTO1.setSupplierName(productTempDTOS1.getSupplierName());
                productOrderQueryDTO1.setBreakfastQty(productTempDTOS1.getBreakfastQty());
                productOrderQueryDTO1.setCurrency(productTempDTOS1.getCurrency());
                productOrderQueryDTO1.setRoomQty(request.getRoomQty());
                productOrderQueryDTO1.setTotalAmt(null == totalMap.get(productTempDTOS1.getProductId()) ?
                        null : totalMap.get(productTempDTOS1.getProductId()).multiply(
                        null == request.getRoomQty() ? BigDecimal.ONE : new BigDecimal(request.getRoomQty())));
                productOrderQueryDTO.add(productOrderQueryDTO1);
        }
        PageInfo<ProductOrderQueryDTO> page = new PageInfo<ProductOrderQueryDTO>(productOrderQueryDTO);
        PaginationSupportDTO<ProductOrderQueryDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(productOrderQueryDTO);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return  paginationSupport;
    }

    @Override
    public PageInfo<ProductLogDTO> queryProductLogList(Map<String, String> requestMap) {
        PageHelper.startPage(Integer.valueOf(requestMap.get("currentPage")), Integer.valueOf(requestMap.get("pageSize")));
        List<ProductLogDTO> productLogDTOS = productMapper.queryProductLogList(requestMap);

        //计算星期几
        String saleDate = requestMap.get("saleDate");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.stringToDate(saleDate));
        String week = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));

        //批量操作时候，可能会有适应星期几的操作，这里处理，若是不在该操作范围内移除
        Iterator<ProductLogDTO> iterator = productLogDTOS.iterator();
        while(iterator.hasNext()){
            ProductLogDTO productLogDTO = iterator.next();
            if(productLogDTO.getOperationWeek() != null &&  StringUtil.isValidString(productLogDTO.getOperationWeek()) && !productLogDTO.getOperationWeek().contains(week)){
                iterator.remove();
            }
        }
        return new PageInfo<ProductLogDTO>(productLogDTOS);
    }

    /**
     * 修改停售状态
     * @param requestMap
     * @return
     */
    @Override
    public int modifyOffShelveStatus(Map<String, String> requestMap) {
        ProductPO productPO = new ProductPO();
        productPO.setProductId(Integer.valueOf(requestMap.get("productId")));
        productPO.setActive(Integer.valueOf(requestMap.get("offShelveStatus")));
        return productMapper.updateByPrimaryKeySelective(productPO);
    }


    /**
     * 组装配额账号
     * @param productDTO
     * @return
     */
    private QuotaAccountPO assemblyQuotaAccount(ProductDTO productDTO) {
        QuotaAccountPO quotaAccountPO = new QuotaAccountPO();
        quotaAccountPO.setHotelId(productDTO.getHotelId());
        quotaAccountPO.setRoomId(productDTO.getRoomId());
        quotaAccountPO.setActive(1);
        quotaAccountPO.setQuotaAccountName(productDTO.getProductName());
        quotaAccountPO.setSupplierCode(productDTO.getSupplierCode());
        quotaAccountPO.setCreatedDt(productDTO.getCreatedDt());
        quotaAccountPO.setCreatedBy(productDTO.getCreatedBy());
        return quotaAccountPO;
    }

    /**
     * 判断是否上架售卖
     * @param productSaleStatusPO
     * @return
     */
    private Boolean checkSaleStatus(ProductSaleStatusPO productSaleStatusPO) {
        if (null != productSaleStatusPO) {
            if (null != productSaleStatusPO.getB2bSaleStatus() && productSaleStatusPO.getB2bSaleStatus() > 0) {//B2B
                return true;
            }
            if (null != productSaleStatusPO.getB2cSaleStatus() && productSaleStatusPO.getB2cSaleStatus() > 0) {//B2C散客
                return true;
            }
            if (null != productSaleStatusPO.getCtripSaleStatus() && productSaleStatusPO.getCtripSaleStatus() > 0) {//携程
                return true;
            }
            if (null != productSaleStatusPO.getFeizhuSaleStatus() && productSaleStatusPO.getFeizhuSaleStatus() > 0) {//飞猪
                return true;
            }
            if (null != productSaleStatusPO.getMeituanSaleStatus() && productSaleStatusPO.getMeituanSaleStatus() > 0) {//美团
                return true;
            }
            if (null != productSaleStatusPO.getTcylSaleStatus() && productSaleStatusPO.getTcylSaleStatus() > 0) {//同程艺龙
                return true;
            }
            if (null != productSaleStatusPO.getQunarSaleStatus() && productSaleStatusPO.getQunarSaleStatus() > 0) {//去哪儿
                return true;
            }
        }
        return false;
    }

    /**
     * 组装酒店产品数据
     * @param productTempDTOList
     * @return
     */
    private HotelProductsDTO assemblyHotelProducts(List<ProductTempDTO> productTempDTOList,List<CompanyChannelPO> companyChannelPOList,Integer hotelId) {
            HotelProductsDTO hotelProductsDTO = new HotelProductsDTO();
        Map<Integer,ProductRoomDTO> roomMap = new HashMap<Integer,ProductRoomDTO>();//房型
        Map<Integer,Map<Integer,ProductForShowDTO>> roomProductsMap = new HashMap<Integer,Map<Integer,ProductForShowDTO>>();//房型对应产品
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
                        ProductForShowDTO productForShowDTO = this.assemblyProduct(productTempDTO,companyChannelPOList);
                        productForShowDTOMap.put(productTempDTO.getProductId(),productForShowDTO);
                        roomProductsMap.put(productTempDTO.getRoomId(),productForShowDTOMap);
                    }else if (null == roomProductsMap.get(productTempDTO.getRoomId()).get(productTempDTO.getProductId())){
                        ProductForShowDTO productForShowDTO = this.assemblyProduct(productTempDTO,companyChannelPOList);
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
                    productRoomDTO.setBedTypes(roomInfoMap.get(roomId).getBedTypes());
                    productRoomDTOList.add(productRoomDTO);
                }
            hotelProductsDTO.setRoomList(productRoomDTOList);
            return hotelProductsDTO;
    }

    /**
     * 组装产品数据
     * @param productTempDTO
     * @param companyChannelPOList
     * @return
     */
    private ProductForShowDTO assemblyProduct(ProductTempDTO productTempDTO,List<CompanyChannelPO> companyChannelPOList) {
        ProductForShowDTO productForShowDTO = BeanUtil.transformBean(productTempDTO,ProductForShowDTO.class);
        StringBuffer saleChannelSB = new StringBuffer();
        StringBuffer noSaleChannelSB = new StringBuffer();
        if (null != companyChannelPOList) {
            StringBuffer channelSb = new StringBuffer();
            companyChannelPOList.forEach(companyChannelPO -> {
                channelSb.append(companyChannelPO.getChannelId()).append(",");
            });
            //商家配置的所有渠道
            String channelStr = channelSb.toString();
            if (channelStr.contains(String.valueOf(ChannelEnum.B2B.no))) {
                if (null != productTempDTO.getB2bSaleStatus() && productTempDTO.getB2bSaleStatus().equals(1)) {
                    saleChannelSB.append(ChannelEnum.B2B.value).append(",");
                }else {
                    noSaleChannelSB.append(ChannelEnum.B2B.value).append(",");
                }
            }

            if (channelStr.contains(String.valueOf(ChannelEnum.B2C.no))) {
                if (null != productTempDTO.getB2cSaleStatus() && productTempDTO.getB2cSaleStatus().equals(1)) {
                    saleChannelSB.append(ChannelEnum.B2C.value).append(",");
                }else {
                    noSaleChannelSB.append(ChannelEnum.B2C.value).append(",");
                }
            }

            if (channelStr.contains(String.valueOf(ChannelEnum.CTRIP.no))) {
                if (null != productTempDTO.getCtripSaleStatus() && productTempDTO.getCtripSaleStatus().equals(1)) {
                    saleChannelSB.append(ChannelEnum.CTRIP.value).append(",");
                }else {
                    noSaleChannelSB.append(ChannelEnum.CTRIP.value).append(",");
                }
            }

            if (channelStr.contains(String.valueOf(ChannelEnum.QUNAR.no))) {
                if (null != productTempDTO.getQunarSaleStatus() && productTempDTO.getQunarSaleStatus().equals(1)) {
                    saleChannelSB.append(ChannelEnum.QUNAR.value).append(",");
                }else {
                    noSaleChannelSB.append(ChannelEnum.QUNAR.value).append(",");
                }
            }

            if (channelStr.contains(String.valueOf(ChannelEnum.FEIZHU.no))) {
                if (null != productTempDTO.getFeizhuSaleStatus() && productTempDTO.getFeizhuSaleStatus().equals(1)) {
                    saleChannelSB.append(ChannelEnum.FEIZHU.value).append(",");
                }else {
                    noSaleChannelSB.append(ChannelEnum.FEIZHU.value).append(",");
                }
            }

            if (channelStr.contains(String.valueOf(ChannelEnum.MEITUAN.no))) {
                if (null != productTempDTO.getMeituanSaleStatus() && productTempDTO.getMeituanSaleStatus().equals(1)) {
                    saleChannelSB.append(ChannelEnum.MEITUAN.value).append(",");
                }else {
                    noSaleChannelSB.append(ChannelEnum.MEITUAN.value).append(",");
                }
            }

            if (channelStr.contains(String.valueOf(ChannelEnum.TCYL.no))) {
                if (null != productTempDTO.getTcylSaleStatus() && productTempDTO.getTcylSaleStatus().equals(1)) {
                    saleChannelSB.append(ChannelEnum.TCYL.value).append(",");
                }else {
                    noSaleChannelSB.append(ChannelEnum.TCYL.value).append(",");
                }
            }
        }
        //销售中渠道
        if (saleChannelSB.length() > 0) {
            productForShowDTO.setOnSaleChannels(saleChannelSB.substring(0,saleChannelSB.length()-1).toString());
        }
        //仓库中渠道
        if (noSaleChannelSB.length() > 0) {
            productForShowDTO.setOffShelveChannels(noSaleChannelSB.substring(0,noSaleChannelSB.length()-1).toString());
        }
        return productForShowDTO;
    }

    /**
     * 组装产品每日行情数据（用于批量调整）
     * type = 1 价格
     * type = 2 房态和配额
     * Type = 2 时，需要查询配额账号Id
     * @param batchQuotationDTOList
     * @return
     */
    private List<ProductDayQuotationDTO> assemblyProductDayQuotation(List<BatchQuotationDTO> batchQuotationDTOList,int type) {
        List<ProductDayQuotationDTO> productDayQuotationDTOList = new ArrayList<ProductDayQuotationDTO>();
        batchQuotationDTOList.forEach(batchQuotationDTO -> {
            //房态为不变时，传-1，设置为null
            if (null != batchQuotationDTO.getRoomStatus() && batchQuotationDTO.getRoomStatus() < 0) {
                batchQuotationDTO.setRoomStatus(null);
            }
            //是否可超为不变时，传-1，设置为null
            if (null != batchQuotationDTO.getOverDraftStatus() && batchQuotationDTO.getOverDraftStatus() < 0) {
                batchQuotationDTO.setOverDraftStatus(null);
            }
            //默认最大调整一年的价格，超过一年自动截取
            Date endDate = DateUtil.stringToDate(batchQuotationDTO.getEndDate());
            if (DateUtil.getDay(DateUtil.getCurrentDate(),endDate) > 365) {
                endDate = DateUtil.getDate(DateUtil.getCurrentDate(),12);//结束时间最大为当前时间加一年
            }
            //type=2时，查询配额账号Id
            ProductPO productPO = new ProductPO();
            productPO.setProductId(batchQuotationDTO.getProductId());
            //TODO 一个个查询可能会有瓶颈
            ProductPO productPO1 = productMapper.selectByPrimaryKey(productPO);
            if (DateUtil.compare(DateUtil.stringToDate(batchQuotationDTO.getStartDate()),endDate) <= 0 && null != productPO1 && null != productPO1.getQuotaAccountId()) {
                List<Date> dateList = DateUtil.getDateInWeekList(DateUtil.stringToDate(batchQuotationDTO.getStartDate()),endDate,batchQuotationDTO.getWeekList());
                if (!CollectionUtils.isEmpty(dateList)) {
                    dateList.forEach(date -> {
                        ProductDayQuotationDTO productDayQuotationDTO = BeanUtil.transformBean(batchQuotationDTO,ProductDayQuotationDTO.class);
                        productDayQuotationDTO.setSaleDate(DateUtil.dateToString(date));
                        productDayQuotationDTO.setModifiedQuota(null != batchQuotationDTO.getModifiedQuota() ? batchQuotationDTO.getModifiedQuota() : 0);
                        if (type == 2) {
                            productDayQuotationDTO.setQuotaAccountId(productPO1.getQuotaAccountId());
                            if (productDayQuotationDTO.getQuotaAdjustmentType().equals(1)) {
                                //调整方式为减少的时候，配额值改为负数
                                productDayQuotationDTO.setModifiedQuota(0-productDayQuotationDTO.getModifiedQuota());
                            }
                        }
                        if(type == 1){
                            if (productDayQuotationDTO.getBasePriceAdjustmentType().equals(1)) {
                                //调整方式为减少的时候，配额值改为负数
                                productDayQuotationDTO.setModifiedBasePrice(BigDecimal.ZERO.subtract(productDayQuotationDTO.getModifiedBasePrice()));
                            }
                        }
                        productDayQuotationDTOList.add(productDayQuotationDTO);
                    });
                }
            }
        });
        return productDayQuotationDTOList;
    }

    /**
     * 筛选修改了的价格房态配额信息
     * @param productDayQuotationList
     */
    private void filterModifyData(List<ProductDayQuotationDTO> productDayQuotationList){

        if(CollectionUtils.isEmpty(productDayQuotationList)){
            return ;
        }

        Map<String, IncrementDTO> incrementMap = new HashMap<String, IncrementDTO>();

        Iterator<ProductDayQuotationDTO> iterator = productDayQuotationList.iterator();
        while(iterator.hasNext()){
            ProductDayQuotationDTO productDayQuotationDTO = iterator.next();

            //从redis中取出来的数据
            ProductBasePriceAndRoomStatusDTO productBasePriceAndRoomStatusDTO = StringUtil.parseObject(redisUtil.hmget(RedisKey.productBasePriceAndRoomStatusKey,StringUtil.concat(String.valueOf(productDayQuotationDTO.getProductId()), "_", productDayQuotationDTO.getSaleDate())), ProductBasePriceAndRoomStatusDTO.class);
            Integer type = -1; //初始化类型，为了做判断


            //价格是否有变化
            if((productDayQuotationDTO.getModifiedBasePrice() != null && productDayQuotationDTO.getBasePriceAdjustmentType() != null)
                    && (productBasePriceAndRoomStatusDTO == null || productBasePriceAndRoomStatusDTO.getBasePrice() == null
                        || !(productDayQuotationDTO.getBasePriceAdjustmentType() == 2 && productBasePriceAndRoomStatusDTO.getBasePrice().equals(productDayQuotationDTO.getModifiedBasePrice())))){
                type = IncrementTypeEnum.PRICE.no;
            }

            //判断判断房态是否有变化
            if(productDayQuotationDTO.getOverDraftStatus() != null && (productBasePriceAndRoomStatusDTO == null || productBasePriceAndRoomStatusDTO.getOverDraftStatus() == null || !productBasePriceAndRoomStatusDTO.getOverDraftStatus().equals(productDayQuotationDTO.getOverDraftStatus()))
              || (productDayQuotationDTO.getRoomStatus() != null && (productBasePriceAndRoomStatusDTO == null || productBasePriceAndRoomStatusDTO.getRoomStatus() == null || !productBasePriceAndRoomStatusDTO.getRoomStatus().equals(productDayQuotationDTO.getRoomStatus())))
              || (productBasePriceAndRoomStatusDTO == null || !(productDayQuotationDTO.getQuotaAdjustmentType() == 2 && productDayQuotationDTO.getModifiedQuota().equals(productBasePriceAndRoomStatusDTO.getRemainingQuota())))){
                     if(type != -1){
                          type = IncrementTypeEnum.PRICE_AND_ROOM_STATUS.no;
                     }else {
                          type = IncrementTypeEnum.ROOM_STATUS.no;
                     }
            }

            if(type != -1){
                if(incrementMap != null && incrementMap.size() > 0 && incrementMap.get(String.valueOf(productDayQuotationDTO.getProductId())) != null){
                    incrementMap.get(String.valueOf(productDayQuotationDTO.getProductId())).getSaleDate().add(productDayQuotationDTO.getSaleDate());
                }else {
                    IncrementDTO incrementDTO = new IncrementDTO();
                    incrementDTO.setProductId(productDayQuotationDTO.getProductId());
                    incrementDTO.setType(type);
                    incrementDTO.setSaleDate(new ArrayList<String>(Arrays.asList(productDayQuotationDTO.getSaleDate())));
                    incrementMap.put(String.valueOf(productDayQuotationDTO.getProductId()), incrementDTO);
                }
            }else {
                iterator.remove();
            }
            productBasePriceAndRoomStatusDTO = null;
        }

        if(incrementMap != null && incrementMap.size() > 0){
            List<String> incrementList = incrementMap.values().stream().map(i -> JSON.toJSONString(i)).collect(Collectors.toList());
            incrementMap = null;
            redisUtil.rpush(RedisKey.incrementKey, incrementList);
        }

    }

    /**
     * 筛选修改的条款信息
     */
    private void handlerProductRestrict(ProductRestrictPO productRestrictPO, IncrementDTO incrementDTO) {
        if(productRestrictPO == null){
            return ;
        }

        ProductRestrictDTO productRestrictDTO = StringUtil.parseObject(redisUtil.hmget(RedisKey.productRestrictKey, String.valueOf(productRestrictPO.getProductId())), ProductRestrictDTO.class);
        Integer type = -1;

        if(productRestrictPO.getCancellationAdvanceDays() != null &&(productRestrictDTO == null || productRestrictDTO.getCancellationAdvanceDays() == null
          || !productRestrictPO.getCancellationAdvanceDays().equals(productRestrictDTO.getCancellationAdvanceDays()))){
            type = IncrementTypeEnum.RESTRICT.no;
        }

        if(productRestrictPO.getCancellationDeductionTerm() != null &&(productRestrictDTO == null || productRestrictDTO.getCancellationDeductionTerm() == null
                || !productRestrictPO.getCancellationDeductionTerm().equals(productRestrictDTO.getCancellationDeductionTerm()))){
            type = IncrementTypeEnum.RESTRICT.no;
        }

        if(productRestrictPO.getCancellationDueTime() != null &&(productRestrictDTO == null || productRestrictDTO.getCancellationDueTime() == null
                || !productRestrictPO.getCancellationDueTime().equals(productRestrictDTO.getCancellationDueTime()))){
            type = IncrementTypeEnum.RESTRICT.no;
        }

        if(productRestrictPO.getCancellationType() != null &&(productRestrictDTO == null || productRestrictDTO.getCancellationType() == null
                || !productRestrictPO.getCancellationType().equals(productRestrictDTO.getCancellationType()))){
            type = IncrementTypeEnum.RESTRICT.no;
        }

        if(productRestrictPO.getComparisonType() != null &&(productRestrictDTO == null || productRestrictDTO.getComparisonType() == null
                || !productRestrictPO.getComparisonType().equals(productRestrictDTO.getComparisonType()))){
            type = IncrementTypeEnum.RESTRICT.no;
        }

        if(productRestrictPO.getReservationAdvanceDays() != null &&(productRestrictDTO == null || productRestrictDTO.getReservationAdvanceDays() == null
                || !productRestrictPO.getReservationAdvanceDays().equals(productRestrictDTO.getReservationAdvanceDays()))){
            type = IncrementTypeEnum.RESTRICT.no;
        }

        if(productRestrictPO.getReservationDueTime() != null &&(productRestrictDTO == null || productRestrictDTO.getReservationDueTime() == null
                || !productRestrictPO.getReservationDueTime().equals(productRestrictDTO.getReservationDueTime()))){
            type = IncrementTypeEnum.RESTRICT.no;
        }

        if(productRestrictPO.getReservationLimitNights() != null &&(productRestrictDTO == null || productRestrictDTO.getReservationLimitNights() == null
                || !productRestrictPO.getReservationLimitNights().equals(productRestrictDTO.getReservationLimitNights()))){
            type = IncrementTypeEnum.RESTRICT.no;
        }

        if(productRestrictPO.getReservationLimitRooms() != null &&(productRestrictDTO == null || productRestrictDTO.getReservationLimitRooms() == null
                || !productRestrictPO.getReservationLimitRooms().equals(productRestrictDTO.getReservationLimitRooms()))){
            type = IncrementTypeEnum.RESTRICT.no;
        }

        if(type != -1){
            if(incrementDTO == null){
                IncrementDTO increment = new IncrementDTO();
                increment.setProductId(productRestrictPO.getProductId());
                increment.setType(type);
                incrementDTO = increment;
            }else {
                incrementDTO.setType(IncrementTypeEnum.RESTRICT_AND_BREAKFAST.no);
            }
            redisUtil.hmset(RedisKey.productRestrictKey, String.valueOf(incrementDTO.getProductId()), JSON.toJSONString(incrementDTO));
        }else {
            productRestrictPO = null;
        }
    }

    /**
     * 筛选修改的早餐
     */
    private IncrementDTO handlerBreakfast(ProductDTO productDTO) {
        if(productDTO == null){
            return null;
        }

        IncrementDTO incrementDTO = new IncrementDTO();
        ProductRestrictDTO productRestrictDTO = StringUtil.parseObject(redisUtil.hmget(RedisKey.productRestrictKey, String.valueOf(productDTO.getProductId())), ProductRestrictDTO.class);

        if(productDTO.getBreakfastQty() != null
                && (productRestrictDTO == null || productRestrictDTO.getBreakfastQty() == null || !productDTO.getBreakfastQty().equals(productRestrictDTO.getBreakfastQty()))){
            incrementDTO.setProductId(productDTO.getProductId());
            incrementDTO.setType(IncrementTypeEnum.BREAKFAST.no);
            return incrementDTO;
        }
       return null;
    }


}
