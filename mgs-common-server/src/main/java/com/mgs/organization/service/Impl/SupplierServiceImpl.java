package com.mgs.organization.service.Impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.enums.EndTypeEnum;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.enums.AvailableEnum;
import com.mgs.enums.DeleteEnum;
import com.mgs.enums.OrgEnum;
import com.mgs.organization.domain.OrgPO;
import com.mgs.organization.domain.UserPO;
import com.mgs.organization.domain.SupplierCompanyPO;
import com.mgs.organization.remote.dto.*;
import com.mgs.organization.mapper.OrgMapper;
import com.mgs.organization.mapper.SupplierCompanyMapper;
import com.mgs.organization.service.SupplierService;
import com.mgs.user.mapper.UserMapper;
import com.mgs.user.service.UserService;
import com.mgs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author py
 * @date 2019/6/19 22:12
 **/
@Service
@Slf4j
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private OrgMapper orgMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SupplierCompanyMapper supplierCompanyMapper;
    @Autowired
    private UserService userService;

    /**
     * 新增供应商，分别操作企业表和共享信息中间表
     *
     * @param supplierAddDTO
     * @return
     */
    @Override
    public Response addSupplier(SupplierAddDTO supplierAddDTO) {
        log.info("addSupplier param: {}" + JSON.toJSONString(supplierAddDTO));
        Response response = new Response();
        
        OrgPO orgPO = new OrgPO();
        SupplierCompanyPO supplierCompanyPO = new SupplierCompanyPO();
        UserPO adminPO = new UserPO();
        BeanUtils.copyProperties(supplierAddDTO, orgPO);
        BeanUtils.copyProperties(supplierAddDTO, supplierCompanyPO);
        BeanUtils.copyProperties(supplierAddDTO, adminPO);
        orgPO.setOrgType(supplierAddDTO.getSupplierType());
        orgPO.setOrgName(supplierAddDTO.getSupplierName());
        orgPO.setOrgDomian(supplierAddDTO.getOrgDomain());
        adminPO.setUserName(supplierAddDTO.getAdminName());
        adminPO.setUserTel(supplierAddDTO.getAdminTel());
        adminPO.setUserAccount(supplierAddDTO.getAdminAccount());
        supplierCompanyPO.setUserNumber(supplierAddDTO.getAdminAccount());
        supplierCompanyPO.setUserTel(supplierAddDTO.getAdminTel());
        supplierCompanyPO.setUserTel(supplierAddDTO.getAdminTel());
        supplierCompanyPO.setSettlementType(supplierAddDTO.getSettlementType());
        supplierCompanyPO.setSettlementCurrency(supplierAddDTO.getSettlementCurrency());
        supplierCompanyPO.setPurchaseManagerId(supplierAddDTO.getPurchaseManagerId());
        supplierCompanyPO.setOperatorCode(supplierAddDTO.getSupplierCode());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        orgPO.setCreatedDt(createdate);
        adminPO.setCreatedDt(createdate);
        orgPO.setAvailableStatus(AvailableEnum.Start.key);
        orgPO.setType(OrgEnum.Org_Supplier.key);
        supplierCompanyPO.setAvailableStatus(AvailableEnum.Start.key);
        supplierCompanyPO.setCreatedDt(createdate);
        orgMapper.insert(orgPO);
        supplierCompanyPO.setOrgId(orgPO.getOrgId());
        supplierCompanyPO.setUserName(supplierAddDTO.getAdminName());
        supplierCompanyPO.setUserNumber(supplierAddDTO.getAdminAccount());
        supplierCompanyPO.setUserTel(supplierAddDTO.getAdminTel());
        supplierCompanyMapper.insert(supplierCompanyPO);
        OrgPO supplierAddPO1 = orgMapper.selectByPrimaryKey(orgPO.getOrgId());
        adminPO.setOrgCode(supplierAddPO1.getOrgCode());
        adminPO.setCreatedBy(supplierAddDTO.getCreatedBy());
        userService.addAdminUser(adminPO, EndTypeEnum._1.no);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    //修改供应商启用状态
    @Override
    public Response modifySupplierStatus(SupplierAddDTO supplierAddDTO) {
        log.info("modifySupplierStatus param: {}" + JSON.toJSONString(supplierAddDTO));
        Response response = new Response();
        OrgPO orgPO = new OrgPO();
        UserPO userPO = new UserPO();
        SupplierCompanyPO supplierCompanyPO = new SupplierCompanyPO();
        orgPO.setAvailableStatus(supplierAddDTO.getAvailableStatus());
        orgPO.setOrgId(supplierAddDTO.getSupplierId());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        OrgPO orgPO1 = orgMapper.selectByPrimaryKey(orgPO);
        Integer userId = orgMapper.getUserId(orgPO1.getOrgCode());
        if (userId != null) {
            userPO.setOrgCode(orgPO1.getOrgCode());
            userPO.setAvailableStatus(supplierAddDTO.getAvailableStatus());
            userPO.setModifiedBy(supplierAddDTO.getModifiedBy());
            userPO.setModifiedDt(createdate);
            orgMapper.updateAdminStatus(userPO);
        }
        supplierCompanyPO.setModifiedBy(supplierAddDTO.getModifiedBy());
        supplierCompanyPO.setModifiedDt(createdate);
        supplierCompanyPO.setOrgId(supplierAddDTO.getSupplierId());
        supplierCompanyPO.setAvailableStatus(supplierAddDTO.getAvailableStatus());
        orgPO.setModifiedDt(createdate);
        orgMapper.updateByPrimaryKeySelective(orgPO);
        orgMapper.updateSupplierStatus(supplierCompanyPO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     * 修改供应商信息
     *
     * @param supplierAddDTO
     * @return
     */
    @Override
    public Response modifySupplier(SupplierAddDTO supplierAddDTO) {
        log.info("modifySupplier param: {}" + JSON.toJSONString(supplierAddDTO));
        Response response = new Response();
        OrgPO orgPO = new OrgPO();
        UserPO userPO = new UserPO();
        SupplierCompanyPO supplierCompanyPO = new SupplierCompanyPO();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        OrgPO supplierAddPO1 = orgMapper.selectByPrimaryKey(supplierAddDTO.getSupplierId());
        Integer userId = orgMapper.getUserId(supplierAddPO1.getOrgCode());
        orgPO.setOrgId(supplierAddDTO.getSupplierId());
        orgPO.setOrgType(supplierAddDTO.getSupplierType());
        orgPO.setOrgName(supplierAddDTO.getSupplierName());
        userPO.setUserName(supplierAddDTO.getAdminName());
        userPO.setUserTel(supplierAddDTO.getAdminTel());
        userPO.setUserAccount(supplierAddDTO.getAdminAccount());
        supplierCompanyPO.setUserName(supplierAddDTO.getAdminName());
        supplierCompanyPO.setUserNumber(supplierAddDTO.getAdminAccount());
        supplierCompanyPO.setUserTel(supplierAddDTO.getAdminTel());
        supplierCompanyPO.setSettlementType(supplierAddDTO.getSettlementType());
        supplierCompanyPO.setSettlementCurrency(supplierAddDTO.getSettlementCurrency());
        supplierCompanyPO.setPurchaseManagerId(supplierAddDTO.getPurchaseManagerId());
        Integer supplierCompany = orgMapper.getSupplierCompany(supplierAddDTO.getSupplierId());
        supplierCompanyPO.setSCompanyId(supplierCompany);
        supplierCompanyPO.setModifiedBy(supplierAddDTO.getModifiedBy());
        supplierCompanyPO.setModifiedDt(createdate);

        if (userId == null) {
            userPO.setOrgCode(supplierAddPO1.getOrgCode());
            userPO.setCreatedBy(supplierAddDTO.getCreatedBy());
            userService.addAdminUser(userPO,EndTypeEnum._1.no);
            orgPO.setModifiedDt(createdate);
            orgPO.setModifiedBy(supplierAddDTO.getModifiedBy());
            orgMapper.updateByPrimaryKeySelective(orgPO);
            supplierCompanyMapper.updateByPrimaryKeySelective(supplierCompanyPO);
            response.setResult(ResultCodeEnum.SUCCESS.code);
        } else {
            userPO.setModifiedBy(supplierAddDTO.getModifiedBy());
            userPO.setUserId(userId);
            userService.modifyAdminUser(userPO);
            orgPO.setModifiedDt(createdate);
            orgPO.setModifiedBy(supplierAddDTO.getModifiedBy());
            orgMapper.updateByPrimaryKeySelective(orgPO);
            supplierCompanyMapper.updateByPrimaryKeySelective(supplierCompanyPO);
            response.setResult(ResultCodeEnum.SUCCESS.code);
        }


        return response;
    }

    /**
     * 根据供应商编码,查询供应商详情
     *
     * @param supplierAddDTO
     * @return
     */
    @Override
    public Response querySupplierDetail(SupplierAddDTO supplierAddDTO) {
        log.info("querySupplierDetail param: {}" + JSON.toJSONString(supplierAddDTO));
        Response response = new Response();
        SupplierSelectDTO supplierSelectDTO = orgMapper.SupplierList(supplierAddDTO.getSupplierCode());
        List<BankSupplierDTO> bankAddDTOS = orgMapper.BankList(supplierAddDTO.getSupplierCode(), DeleteEnum.STATUS_EXIST.key);
        List<ContactSupplierDTO> contactAddDTOS = orgMapper.ConcactList(supplierAddDTO.getSupplierCode(), DeleteEnum.STATUS_EXIST.key);
        if (bankAddDTOS.size() != 0) {
            supplierSelectDTO.setBankCardList(bankAddDTOS);
        }
        if (contactAddDTOS.size() != 0) {
            supplierSelectDTO.setContactList(contactAddDTOS);
        }
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(supplierSelectDTO);
        return response;
    }

    @Override
    public PaginationSupportDTO<QuerySupplierListDTO> querySupplierList(SupplierListRequest request) {
        Response response = new Response();
        PageHelper.startPage(request.getCurrentPage(), request.getPageSize());
        List<QuerySupplierListDTO> list = orgMapper.querySupplierList(request);
        PageInfo<QuerySupplierListDTO> page = new PageInfo<QuerySupplierListDTO>(list);
        PaginationSupportDTO<QuerySupplierListDTO> paginationSupport=new PaginationSupportDTO<>();
        paginationSupport.setItemList(list);
        paginationSupport.setPageSize(page.getPageSize());
        paginationSupport.setTotalCount(page.getTotal());
        paginationSupport.setTotalPage(page.getPages());
        paginationSupport.setCurrentPage(page.getPageNum());
        return  paginationSupport;
    }


}
