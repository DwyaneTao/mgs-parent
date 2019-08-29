package com.mgs.organization.service.Impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.EndTypeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.enums.AvailableEnum;
import com.mgs.enums.OrgEnum;
import com.mgs.enums.PictureTypeEnum;
import com.mgs.organization.domain.CompanyDomainPO;
import com.mgs.organization.domain.OrgPO;
import com.mgs.organization.domain.PicturePO;
import com.mgs.organization.domain.UserPO;
import com.mgs.organization.mapper.CompanyDomainMapper;
import com.mgs.organization.remote.dto.*;
import com.mgs.organization.mapper.OrgMapper;
import com.mgs.organization.mapper.PictureMapper;
import com.mgs.organization.service.CompanyService;
import com.mgs.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author py
 * @date 2019/6/28 11:33
 **/
@Service
@Slf4j
public class CompanyServiceImpl  implements CompanyService {
    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private OrgMapper orgMapper;
    @Autowired
    private CompanyDomainMapper companyDomainMapper;
    @Autowired
    private UserService userService;
    @Override
    /**
     * 添加运营商信息
     */
    public Response addCompany(CompanyAddDTO companyAddDTO) {
        log.info("addCompany param: {}"+ JSON.toJSONString(companyAddDTO));
        Response response = new Response();
        OrgPO orgPO = new OrgPO();
        PicturePO picturePO = new PicturePO();
        UserPO userPO = new UserPO();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        orgPO.setAvailableStatus(AvailableEnum.Start.key);
       orgPO.setCreatedDt(createdate);
       orgPO.setCreatedBy(companyAddDTO.getCreatedBy());
       orgPO.setOrgName(companyAddDTO.getCompanyName());
       orgPO.setOrgDomian(companyAddDTO.getCompanyDomain());
       orgPO.setHotelInfoPermissions(companyAddDTO.getHotelInfoPermissions());
       userPO.setUserName(companyAddDTO.getAdminName());
       userPO.setUserAccount(companyAddDTO.getAdminAccount());
       userPO.setUserTel(companyAddDTO.getAdminTel());
       userPO.setCreatedBy(companyAddDTO.getCreatedBy());
       orgPO.setOrgTel(companyAddDTO.getCompanyTel());
       orgPO.setOrgAddress(companyAddDTO.getCompanyAddress());
       orgPO.setOrgSummary(companyAddDTO.getCompanySummary());
       orgPO.setType(OrgEnum.Org_Company.key);
       orgMapper.insert(orgPO);
       OrgPO supplierAddPO1 = orgMapper.selectByPrimaryKey(orgPO.getOrgId());
       userPO.setOrgCode(supplierAddPO1.getOrgCode());
        for (int i = 0; i < companyAddDTO.getPictureList().size(); i++) {
            BeanUtils.copyProperties(companyAddDTO.getPictureList().get(i),picturePO);
            picturePO.setCreatedBy(companyAddDTO.getCreatedBy());
            picturePO.setCreatedDt(createdate);
            picturePO.setCompanyCode(supplierAddPO1.getOrgCode());
            pictureMapper.insert(picturePO);
        }
        userService.addAdminUser(userPO, EndTypeEnum._0.no);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 修改运营商信息
     * @param companyAddDTO
     * @return
     */
    @Override
    public Response modifyCompany(CompanyAddDTO companyAddDTO) {
        log.info("modifyCompany param: {}"+ JSON.toJSONString(companyAddDTO));
        Response response = new Response();
        int n = 0;
        OrgPO orgPO = new OrgPO();
        PicturePO picturePO = new PicturePO();
        UserPO userPO = new UserPO();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        orgPO.setModifiedDt(createdate);
        orgPO.setModifiedBy(companyAddDTO.getModifiedBy());
        orgPO.setOrgName(companyAddDTO.getCompanyName());
        orgPO.setOrgDomian(companyAddDTO.getCompanyDomain());
        userPO.setUserName(companyAddDTO.getAdminName());
        userPO.setUserAccount(companyAddDTO.getAdminAccount());
        userPO.setUserTel(companyAddDTO.getAdminTel());
        userPO.setModifiedBy(companyAddDTO.getModifiedBy());
        orgPO.setOrgTel(companyAddDTO.getCompanyTel());
        orgPO.setOrgAddress(companyAddDTO.getCompanyAddress());
        orgPO.setOrgSummary(companyAddDTO.getCompanySummary());
        orgPO.setOrgId(companyAddDTO.getCompanyId());
        orgPO.setHotelInfoPermissions(companyAddDTO.getHotelInfoPermissions());
        OrgPO supplierAddPO1 = orgMapper.selectByPrimaryKey(orgPO.getOrgId());
        Integer userId = orgMapper.getUserId(supplierAddPO1.getOrgCode());
        if(userId==null){
            userPO.setOrgCode(supplierAddPO1.getOrgCode());
            userPO.setCreatedBy(companyAddDTO.getCreatedBy());
            userService.addAdminUser(userPO,EndTypeEnum._0.no);
        }
        else {
            userPO.setModifiedBy(companyAddDTO.getModifiedBy());
            userPO.setUserId(userId);
            userService.modifyAdminUser(userPO);
        }
        orgMapper.updateByPrimaryKeySelective(orgPO);
        List<PictureLicenseDTO> pictureLicenseDTOS = pictureMapper.queryPictureLicense(supplierAddPO1.getOrgCode(),PictureTypeEnum.ONE.key);
        if(pictureLicenseDTOS.size()!=0) {
            for (int i = 0; i < companyAddDTO.getPictureList().size(); i++) {
                BeanUtils.copyProperties(companyAddDTO.getPictureList().get(i), picturePO);
                if (companyAddDTO.getPictureList().get(i).getPictureType() == 0) {
                    for (int j = 0; j < pictureLicenseDTOS.size(); j++) {
                        if (companyAddDTO.getPictureList().get(i).getPictureUrl().equals(pictureLicenseDTOS.get(j).getPictureUrl())) {
                            n++;
                        }
                    }
                    if (n == 0) {
                        picturePO.setCreatedDt(createdate);
                        picturePO.setCreatedBy(companyAddDTO.getModifiedBy());
                        picturePO.setPictureUrl(companyAddDTO.getPictureList().get(i).getPictureUrl());
                        picturePO.setCompanyCode(supplierAddPO1.getOrgCode());
                        picturePO.setPictureName(companyAddDTO.getPictureList().get(i).getPictureName());
                        picturePO.setPictureType(PictureTypeEnum.ONE.key);
                        pictureMapper.insert(picturePO);
                    }
                } else {
                    List<PictureLicenseDTO> pictureLicenseDTOS1 = pictureMapper.queryPictureLicense(supplierAddPO1.getOrgCode(), PictureTypeEnum.TWO.key);
                    if(pictureLicenseDTOS1!=null) {
                        picturePO.setModifiedBy(companyAddDTO.getModifiedBy());
                        picturePO.setModifiedDt(createdate);
                        picturePO.setPictureUrl(companyAddDTO.getPictureList().get(i).getPictureUrl());
                        picturePO.setCompanyCode(supplierAddPO1.getOrgCode());
                        picturePO.setPictureName(companyAddDTO.getPictureList().get(i).getPictureName());
                        picturePO.setPictureType(PictureTypeEnum.TWO.key);
                        picturePO.setPictureId(pictureLicenseDTOS1.get(0).getPictureId());
                    }
                    pictureMapper.updateByPrimaryKeySelective(picturePO);
                }
            }
        }
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 修改运营商启用状态
     * @param companyAddDTO
     * @return
     */
    @Override
    public Response modifyCompanyStatus(CompanyAddDTO companyAddDTO) {
        log.info("modifyCompanyStatus param: {}"+ JSON.toJSONString(companyAddDTO));
        Response response = new Response();
        OrgPO orgPO = new OrgPO();
        orgPO.setAvailableStatus(companyAddDTO.getAvailableStatus());
        orgPO.setOrgId(companyAddDTO.getCompanyId());
        UserPO userPO = new UserPO();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        orgPO.setModifiedDt(createdate);
        OrgPO orgPO1 = orgMapper.selectByPrimaryKey(orgPO);
        Integer userId = orgMapper.getUserId(orgPO1.getOrgCode());
        if(userId!=null){
            userPO.setOrgCode(orgPO1.getOrgCode());
            userPO.setAvailableStatus(companyAddDTO.getAvailableStatus());
            userPO.setModifiedBy(companyAddDTO.getModifiedBy());
            userPO.setModifiedDt(createdate);
            orgMapper.updateAdminStatus(userPO);
        }
        orgMapper.updateByPrimaryKeySelective(orgPO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 查询运营商详情
     * @param companyAddDTO
     * @return
     */
    @Override
    public Response queryCompanyDetail(CompanyAddDTO companyAddDTO) {
        log.info("queryCompanyDetail param: {}"+ JSON.toJSONString(companyAddDTO));
        Response response = new Response();
        CompanySelectDTO companySelectDTO = orgMapper.companyList(companyAddDTO.getCompanyCode());
        List<CompanyBusinessLicenseUrlDTO> pictureLicenseDTOS = pictureMapper.PictureLicense(companyAddDTO.getCompanyCode(), PictureTypeEnum.ONE.key);
        List<CompanyBusinessLicenseUrlDTO> pictureLicenseDTOS1 = pictureMapper.PictureLicense(companyAddDTO.getCompanyCode(), PictureTypeEnum.TWO.key);
        if(pictureLicenseDTOS.size()!=0){
            companySelectDTO.setPictureList(pictureLicenseDTOS);

        }
        if(pictureLicenseDTOS1.size()!=0){
            companySelectDTO.setCompanyLogoUrl(pictureLicenseDTOS1.get(0).getCompanyBusinessLicenseUrl());
            }
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(companySelectDTO);
        return response;
    }
    /**
     * 运营商列表
     */
    @Override
    public PaginationSupportDTO<QueryCompanyListDTO> queryCompanyList(CompanyListRequest request) {
        Response response = new Response();
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<QueryCompanyListDTO> list = orgMapper.queryCompanyList(request);
        PageInfo<QueryCompanyListDTO> page = new PageInfo<QueryCompanyListDTO>(list);
        PaginationSupportDTO<QueryCompanyListDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return  paginationSupport;
    }

    @Override
    public Response isCompanyExit(CompanyAddDTO companyAddDTO) {
        Response response = new Response();
        CompanyDomainPO companyDomainPO = new CompanyDomainPO();
        BeanUtils.copyProperties(companyAddDTO,companyDomainPO);
        CompanySelectDTO domian = orgMapper.getDomian(companyAddDTO.getCompanyDomain());
        isExitDTO isExitDTO = new isExitDTO();
        int isExit = 0;
        if (domian==null){
            isExit = 0;
        }
        else {
            isExit = 1;
        }
        isExitDTO.setIsExit(isExit);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(isExitDTO);
        return response;
    }
    @Override
    public Response examineOrgName(@RequestBody ExamineOrgNameDTO examineOrgNameDTO) {
        Response response = new Response();
        if (examineOrgNameDTO.getType().equals(0)){
            String supplierName = orgMapper.getSupplierName(examineOrgNameDTO);
            if(supplierName==null){
                response.setModel(0);
            }
            else {
                response.setModel(1);
            }
        }
        else  if(examineOrgNameDTO.getType().equals(1)){
            String agentName = orgMapper.getAgentName(examineOrgNameDTO);
            if(agentName==null){
                response.setModel(0);
            }
            else {
                response.setModel(1);
            }
        }
        else  if(examineOrgNameDTO.getType().equals(2)){
            String companyName = orgMapper.getCompanyName(examineOrgNameDTO);
            if(companyName==null){
                response.setModel(0);
            }
            else {
                response.setModel(1);
            }
        }
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

}




