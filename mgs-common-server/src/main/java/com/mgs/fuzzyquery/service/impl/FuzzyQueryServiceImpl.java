package com.mgs.fuzzyquery.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.ErrorCodeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.fuzzyquery.dto.*;
import com.mgs.fuzzyquery.mapper.FuzzyQueryMapper;
import com.mgs.fuzzyquery.service.FuzzyQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/7/2 11:34
 * @Description: 模糊查询接口
 */
@Slf4j
@Service("fuzzyQueryService")
public class FuzzyQueryServiceImpl implements FuzzyQueryService {

    @Autowired
    private FuzzyQueryMapper fuzzyQueryMapper;

    /**
     * 模糊查询城市
     * @param fuzzyQueryDTO
     * @return
     */
    public Response queryCity(FuzzyQueryDTO fuzzyQueryDTO) {
        Response response = new Response(1);
        PaginationSupportDTO<FuzzyCityDTO> paginationSupportDTO = new PaginationSupportDTO<FuzzyCityDTO>();

        try {
            PageHelper.startPage(fuzzyQueryDTO.getCurrentPage(),fuzzyQueryDTO.getPageSize());
            List<FuzzyCityDTO> fuzzyCityDTOList = fuzzyQueryMapper.fuzzyQueryCity(fuzzyQueryDTO);
            PageInfo<FuzzyCityDTO> page = new PageInfo<FuzzyCityDTO>(fuzzyCityDTOList);
            paginationSupportDTO.setItemList(fuzzyCityDTOList);
            paginationSupportDTO.setCurrentPage(page.getPageNum());
            paginationSupportDTO.setPageSize(page.getPageSize());
            paginationSupportDTO.setTotalCount(page.getTotal());
            paginationSupportDTO.setTotalPage(page.getPages());
            response.setModel(paginationSupportDTO);
        }catch (Exception e) {
            log.error("queryCity-service error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 模糊查询供应商
     * @param fuzzyQueryDTO
     * @return
     */
    public Response querySupplier(FuzzyQueryDTO fuzzyQueryDTO) {
        Response response = new Response(1);
        PaginationSupportDTO<FuzzySupplierDTO> paginationSupportDTO = new PaginationSupportDTO<FuzzySupplierDTO>();

        try {
            PageHelper.startPage(fuzzyQueryDTO.getCurrentPage(),fuzzyQueryDTO.getPageSize());
            List<FuzzySupplierDTO> fuzzySupplierDTOList = fuzzyQueryMapper.fuzzyQuerySupplier(fuzzyQueryDTO);
            PageInfo<FuzzySupplierDTO> page = new PageInfo<FuzzySupplierDTO>(fuzzySupplierDTOList);
            paginationSupportDTO.setItemList(fuzzySupplierDTOList);
            paginationSupportDTO.setCurrentPage(page.getPageNum());
            paginationSupportDTO.setPageSize(page.getPageSize());
            paginationSupportDTO.setTotalCount(page.getTotal());
            paginationSupportDTO.setTotalPage(page.getPages());
            response.setModel(paginationSupportDTO);
        }catch (Exception e) {
            log.error("querySupplier-service error!",e);
            response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
        }
        return response;
    }

    /**
     * 模糊查询分销商
     * @param fuzzyQueryDTO
     * @return
     */
     public Response queryAgent(FuzzyQueryDTO fuzzyQueryDTO) {
         Response response = new Response(1);
         PaginationSupportDTO<FuzzyAgentDTO> paginationSupportDTO = new PaginationSupportDTO<FuzzyAgentDTO>();

         try {
             PageHelper.startPage(fuzzyQueryDTO.getCurrentPage(),fuzzyQueryDTO.getPageSize());
             List<FuzzyAgentDTO> fuzzyAgentDTOList = fuzzyQueryMapper.fuzzyQueryAgent(fuzzyQueryDTO);
             PageInfo<FuzzyAgentDTO> page = new PageInfo<FuzzyAgentDTO>(fuzzyAgentDTOList);
             paginationSupportDTO.setItemList(fuzzyAgentDTOList);
             paginationSupportDTO.setCurrentPage(page.getPageNum());
             paginationSupportDTO.setPageSize(page.getPageSize());
             paginationSupportDTO.setTotalCount(page.getTotal());
             paginationSupportDTO.setTotalPage(page.getPages());
             response.setModel(paginationSupportDTO);
         }catch (Exception e) {
             log.error("queryAgent-service error!",e);
             response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
         }
         return response;
     }

    /**
     * 模糊查询酒店
     * @param fuzzyQueryDTO
     * @return
     */
     public Response queryHotel(FuzzyQueryDTO fuzzyQueryDTO) {
         Response response = new Response(1);
         PaginationSupportDTO<FuzzyHotelDTO> paginationSupportDTO = new PaginationSupportDTO<FuzzyHotelDTO>();

         try {
             PageHelper.startPage(fuzzyQueryDTO.getCurrentPage(),fuzzyQueryDTO.getPageSize());
             List<FuzzyHotelDTO> fuzzyHotelDTOList = fuzzyQueryMapper.fuzzyQueryHotel(fuzzyQueryDTO);
             PageInfo<FuzzyHotelDTO> page = new PageInfo<FuzzyHotelDTO>(fuzzyHotelDTOList);
             paginationSupportDTO.setItemList(fuzzyHotelDTOList);
             paginationSupportDTO.setCurrentPage(page.getPageNum());
             paginationSupportDTO.setPageSize(page.getPageSize());
             paginationSupportDTO.setTotalCount(page.getTotal());
             paginationSupportDTO.setTotalPage(page.getPages());
             response.setModel(paginationSupportDTO);
         }catch (Exception e) {
             log.error("queryHotel-service error!",e);
             response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
         }
         return response;
     }

    /**
     * 模糊查询房型
     * @param fuzzyQueryDTO
     * @return
     */
     public Response queryRoom(FuzzyQueryDTO fuzzyQueryDTO) {
         Response response = new Response(1);
         PaginationSupportDTO<FuzzyRoomDTO> paginationSupportDTO = new PaginationSupportDTO<FuzzyRoomDTO>();

         try {
             PageHelper.startPage(fuzzyQueryDTO.getCurrentPage(),fuzzyQueryDTO.getPageSize());
             List<FuzzyRoomDTO> fuzzyRoomDTOList = fuzzyQueryMapper.fuzzyQueryRoom(fuzzyQueryDTO);
             PageInfo<FuzzyRoomDTO> page = new PageInfo<FuzzyRoomDTO>(fuzzyRoomDTOList);
             paginationSupportDTO.setItemList(fuzzyRoomDTOList);
             paginationSupportDTO.setCurrentPage(page.getPageNum());
             paginationSupportDTO.setPageSize(page.getPageSize());
             paginationSupportDTO.setTotalCount(page.getTotal());
             paginationSupportDTO.setTotalPage(page.getPages());
             response.setModel(paginationSupportDTO);
         }catch (Exception e) {
             log.error("queryRoom-service error!",e);
             response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
         }
         return response;
     }

    /**
     * 模糊查询产品
     * @param fuzzyQueryDTO
     * @return
     */
     public Response queryProduct(FuzzyQueryDTO fuzzyQueryDTO) {
         Response response = new Response(1);
         PaginationSupportDTO<FuzzyProductDTO> paginationSupportDTO = new PaginationSupportDTO<FuzzyProductDTO>();

         try {
             PageHelper.startPage(fuzzyQueryDTO.getCurrentPage(),fuzzyQueryDTO.getPageSize());
             List<FuzzyProductDTO> fuzzyProductDTOList = fuzzyQueryMapper.fuzzyQueryProduct(fuzzyQueryDTO);
             PageInfo<FuzzyProductDTO> page = new PageInfo<FuzzyProductDTO>(fuzzyProductDTOList);
             paginationSupportDTO.setItemList(fuzzyProductDTOList);
             paginationSupportDTO.setCurrentPage(page.getPageNum());
             paginationSupportDTO.setPageSize(page.getPageSize());
             paginationSupportDTO.setTotalCount(page.getTotal());
             paginationSupportDTO.setTotalPage(page.getPages());
             response.setModel(paginationSupportDTO);
         }catch (Exception e) {
             log.error("queryProduct-service error!",e);
             response = new Response(ResultCodeEnum.FAILURE.code, ErrorCodeEnum.SYSTEM_EXCEPTION.errorCode,ErrorCodeEnum.SYSTEM_EXCEPTION.errorDesc);
         }
         return response;
     }
}
