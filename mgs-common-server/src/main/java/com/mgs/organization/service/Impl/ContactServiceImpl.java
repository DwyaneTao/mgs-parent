package com.mgs.organization.service.Impl;

import com.alibaba.fastjson.JSON;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.enums.ContactEnum;
import com.mgs.enums.DeleteEnum;
import com.mgs.organization.domain.ContactPO;
import com.mgs.organization.mapper.OrgMapper;
import com.mgs.organization.remote.dto.ContactAddDTO;
import com.mgs.organization.mapper.ContactMapper;
import com.mgs.organization.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author py
 * @date 2019/6/22 11:46
 **/
@Service
@Slf4j
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private OrgMapper orgMapper;
    @Override
    public Response addContact(ContactAddDTO ContactAddDTO) {
        log.info("addContact param: {}"+ JSON.toJSONString(ContactAddDTO));
        Response response = new Response();
        ContactPO contactPO = new ContactPO();
        BeanUtils.copyProperties(ContactAddDTO,contactPO);
        contactPO.setActive(DeleteEnum.STATUS_EXIST.key);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        contactPO.setCreatedDt(createdate);
        if(ContactAddDTO.getContactRole().equals("0")){
            orgMapper.updateContactRole(ContactAddDTO.getOrgCode(), "",ContactAddDTO.getContactRole());
            orgMapper.updateContactRole(ContactAddDTO.getOrgCode(), "1","0,1");
        }
        if(ContactAddDTO.getContactRole().equals("1")){
            orgMapper.updateContactRole(ContactAddDTO.getOrgCode(), "",ContactAddDTO.getContactRole());
            orgMapper.updateContactRole(ContactAddDTO.getOrgCode(), "0","0,1");
        }
        else if(ContactAddDTO.getContactRole().equals("0,1")){
            orgMapper.updateContactRole(ContactAddDTO.getOrgCode(), "","0");
            orgMapper.updateContactRole(ContactAddDTO.getOrgCode(), "","1");
            orgMapper.updateContactRole(ContactAddDTO.getOrgCode(), "","0,1");
        }
        contactMapper.insert(contactPO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response modifyContact(ContactAddDTO contactAddDTO) {
        log.info("modifyContact param: {}"+ JSON.toJSONString(contactAddDTO));
        Response response = new Response();
        ContactPO contactPO = new ContactPO();
        BeanUtils.copyProperties(contactAddDTO,contactPO);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        contactPO.setModifiedDt(createdate);
        if(contactAddDTO.getContactRole().equals("")||contactAddDTO.getContactRole().equals(null)){
            contactMapper.updateByPrimaryKeySelective(contactPO);
        }
        else if(contactAddDTO.getContactRole().equals("0")){
            orgMapper.updateContactRole(contactAddDTO.getOrgCode(), "",contactAddDTO.getContactRole());
            orgMapper.updateContactRole(contactAddDTO.getOrgCode(), "1","0,1");
            contactMapper.updateByPrimaryKeySelective(contactPO);
        }
        else if(contactAddDTO.getContactRole().equals("1")){
            orgMapper.updateContactRole(contactAddDTO.getOrgCode(), "",contactAddDTO.getContactRole());
            orgMapper.updateContactRole(contactAddDTO.getOrgCode(), "0","0,1");
            contactMapper.updateByPrimaryKeySelective(contactPO);
        }
        else if(contactAddDTO.getContactRole().equals("0,1")){
            orgMapper.updateContactRole(contactAddDTO.getOrgCode(), "","0");
            orgMapper.updateContactRole(contactAddDTO.getOrgCode(), "","1");
            orgMapper.updateContactRole(contactAddDTO.getOrgCode(), "","0,1");
            contactMapper.updateByPrimaryKeySelective(contactPO);
        }
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response deleteContact(ContactAddDTO contactAddDTO) {
        log.info("deleteContact param: {}"+ JSON.toJSONString(contactAddDTO));
        Response response = new Response();
        ContactPO contactPO = new ContactPO();
        BeanUtils.copyProperties(contactAddDTO, contactPO);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        contactPO.setCreatedDt(createdate);
        contactPO.setActive(DeleteEnum.STATUS_DELECT.key);
        contactMapper.updateByPrimaryKeySelective(contactPO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }
}
