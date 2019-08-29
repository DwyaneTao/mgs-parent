package com.mgs.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ChannelEnum;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.hotel.dto.BasicRoomInfoDTO;
import com.mgs.hotel.remote.HotelRemote;
import com.mgs.product.domain.CompanyChannelPO;
import com.mgs.product.domain.RoomCharterPO;
import com.mgs.product.dto.HotelProductsDTO;
import com.mgs.product.dto.ProductForShowDTO;
import com.mgs.product.dto.ProductRoomDTO;
import com.mgs.product.dto.ProductSaleItemDTO;
import com.mgs.product.dto.ProductTempDTO;
import com.mgs.product.dto.QueryProductRequestDTO;
import com.mgs.product.dto.QueryRoomCharterDetailDTO;
import com.mgs.product.dto.RoomCharterDTO;
import com.mgs.product.dto.RoomCharterQueryDTO;
import com.mgs.product.dto.RoomCharterQueryQuestDTO;
import com.mgs.product.mapper.CompanyChannelMapper;
import com.mgs.product.mapper.ProductMapper;
import com.mgs.product.mapper.RoomCharterMapper;
import com.mgs.product.service.ProductService;
import com.mgs.product.service.RoomCharterService;
import com.mgs.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author py
 * @date 2019/7/18 16:47
 **/
@Slf4j
@Service("roomService")
public class RoomCharterServiceImpl implements RoomCharterService {
    @Autowired
    private RoomCharterMapper roomCharterMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CompanyChannelMapper companyChannelMapper;
    @Autowired
    private HotelRemote hotelRemote;
    @Override
    public Response addRoomCharter(RoomCharterDTO roomCharterDTO) {
        Response response = new Response();
        RoomCharterPO roomCharterPO = new RoomCharterPO();
        BeanUtils.copyProperties(roomCharterDTO,roomCharterPO);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String datetime = sdf.format(date);
        roomCharterPO.setCreatedDt(datetime);
        roomCharterPO.setCreatedBy(roomCharterDTO.getCreatedBy());
        roomCharterMapper.insert(roomCharterPO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response modifyRoomCharter(RoomCharterDTO roomCharterDTO) {
        Response response = new Response();
        RoomCharterPO roomCharterPO = new RoomCharterPO();
        BeanUtils.copyProperties(roomCharterDTO,roomCharterPO);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String datetime = sdf.format(date);
        roomCharterPO.setModifiedBy(datetime);
        roomCharterPO.setModifiedBy(roomCharterDTO.getModifiedBy());
        roomCharterMapper.updateByPrimaryKeySelective(roomCharterPO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public PaginationSupportDTO<RoomCharterQueryDTO> queryRoomCharterList(RoomCharterQueryQuestDTO request) {
        log.info("queryRoomCharterList param: {}"+ JSON.toJSONString(request));
        Response response = new Response();
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<RoomCharterQueryDTO> list = roomCharterMapper.queryRoomCharterList(request);
        PageInfo<RoomCharterQueryDTO> page = new PageInfo<RoomCharterQueryDTO>(list);
        PaginationSupportDTO<RoomCharterQueryDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return  paginationSupport;
    }

    @Override
    public Response queryRoomCharterDetail(RoomCharterQueryQuestDTO request) {
        Response response = new Response();
        QueryRoomCharterDetailDTO queryRoomCharterDetailDTO = roomCharterMapper.queryRoomCharterDetail(request.getRoomCharterCode());
        Integer soldNightQty = roomCharterMapper.getSoldNightQty(request.getRoomCharterCode());
        queryRoomCharterDetailDTO.setSoldNightQty(soldNightQty);
        queryRoomCharterDetailDTO.setRemainingNightQty(queryRoomCharterDetailDTO.getNightQty()-soldNightQty);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(queryRoomCharterDetailDTO);
        return  response;
    }

    @Override
    public Response charterRoomSales(QueryProductRequestDTO request) {
        Response response = new Response(1);
        try {
            request.setPurchaseType(1);
            HotelProductsDTO hotelProductsDTO = new HotelProductsDTO();
            //查询酒店产品列表
            List<ProductTempDTO> productTempDTOList = roomCharterMapper.queryHotelProducts(request);
            for (ProductTempDTO productTempDTO:productTempDTOList){
                productTempDTO.setQuota(productTempDTO.getRemainingQuota()+productTempDTO.getSoldQuota());
            }
            Map<Integer,ProductTempDTO> productTempDTOMap = new HashMap<>();
            //查询运营商对应渠道
            Example companyChannelExample = new Example(CompanyChannelPO.class);
            Example.Criteria companyChannelCriteria = companyChannelExample.createCriteria();
            companyChannelCriteria.andEqualTo("companyCode",request.getCompanyCode());
            List<CompanyChannelPO> companyChannelPOList = companyChannelMapper.selectByExample(companyChannelExample);
            //组装酒店产品数据
            hotelProductsDTO = this.assemblyHotelProducts(productTempDTOList,companyChannelPOList,request.getHotelId());

            response.setModel(hotelProductsDTO);
        }catch (Exception e) {
            log.error("charterRoomSales-service error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 组装包房产品数据
     */
    private HotelProductsDTO assemblyHotelProducts(List<ProductTempDTO> productTempDTOList,List<CompanyChannelPO> companyChannelPOList,Integer hotelId) {
        HotelProductsDTO hotelProductsDTO = new HotelProductsDTO();
        Map<Integer,ProductRoomDTO> roomMap = new HashMap<Integer,ProductRoomDTO>();//房型
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
                Integer remainingQuota = 0;
                Integer soldQuota = 0;
                for (ProductSaleItemDTO productSaleItemDTO : productSaleItemDTOMap.get(productId)) {
                    remainingQuota += productSaleItemDTO.getRemainingQuota();
                    soldQuota += productSaleItemDTO.getSoldQuota();
                }
                roomProductsMap.get(roomId).get(productId).setQuota(remainingQuota+soldQuota);
                roomProductsMap.get(roomId).get(productId).setRemainingQuota(remainingQuota);
                roomProductsMap.get(roomId).get(productId).setSoldQuota(soldQuota);
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



}
