package com.mgs.organization.service.Impl;

import com.alibaba.fastjson.JSON;
import com.mgs.common.Response;
import com.mgs.enums.ResultCodeEnum;
import com.mgs.enums.DeleteEnum;
import com.mgs.organization.domain.BankPO;
import com.mgs.organization.remote.dto.BankAddDTO;
import com.mgs.organization.remote.dto.BankSupplierDTO;
import com.mgs.organization.mapper.BankMapper;
import com.mgs.organization.mapper.OrgMapper;
import com.mgs.organization.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author py
 * @date 2019/6/21 21:59
 **/
@Service
@Slf4j
public class BankServiceImpl implements BankService {
    @Autowired
    private BankMapper bankMapper;
    @Autowired
    private OrgMapper orgMapper;

    /**
     *新增银行卡信息
     * @param BankAddDTO
     * @return
     */
    @Override
    public Response addBank(BankAddDTO BankAddDTO) {
        log.info("addBank param: {}"+ JSON.toJSONString(BankAddDTO));
        Response response = new Response();
        BankPO bankPO = new BankPO();
        BeanUtils.copyProperties(BankAddDTO,bankPO);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        bankPO.setCreatedDt(createdate);
        bankPO.setActive(DeleteEnum.STATUS_EXIST.key);
        bankMapper.insert(bankPO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    /**
     *修改银行卡信息
     * @param BankAddDTO
     * @return
     */
    @Override
    public Response modifyBank(BankAddDTO BankAddDTO) {
        log.info("updateBank param: {}"+ JSON.toJSONString(BankAddDTO));
        Response response = new Response();
        BankPO bankPO = new BankPO();
        BeanUtils.copyProperties(BankAddDTO,bankPO);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        bankPO.setModifiedDt(createdate);
        bankMapper.updateByPrimaryKeySelective(bankPO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }
    /**
     *删除银行卡信息
     * @param BankAddDTO
     * @return
     */
    @Override
    public Response deleteBank(BankAddDTO BankAddDTO) {
        log.info("deleteBank param: {}"+ JSON.toJSONString(BankAddDTO));
        Response response = new Response();
        BankPO bankPO = new BankPO();
        BeanUtils.copyProperties(BankAddDTO, bankPO);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        bankPO.setModifiedDt(createdate);
        bankPO.setActive(DeleteEnum.STATUS_DELECT.key);
        bankMapper.updateByPrimaryKeySelective(bankPO);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        return response;
    }

    @Override
    public Response QueryBankList(BankAddDTO bankAddDTO) {
        Response response = new Response();
        List<BankSupplierDTO> bankSupplierDTOS = orgMapper.BankList(bankAddDTO.getOrgCode(), DeleteEnum.STATUS_EXIST.key);
        response.setResult(ResultCodeEnum.SUCCESS.code);
        response.setModel(bankSupplierDTOS);
        return response;
    }
}
