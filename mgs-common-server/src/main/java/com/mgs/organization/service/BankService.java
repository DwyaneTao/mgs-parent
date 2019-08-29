package com.mgs.organization.service;

import com.mgs.common.Response;
import com.mgs.organization.remote.dto.BankAddDTO;

/**
 * @author py
 * @date 2019/6/21 21:58
 **/
public interface BankService {
    /**
     * 新增银行
     * @param
     * @return
     */
    Response addBank(BankAddDTO BankAddDTO);
    /**
     * 修改银行卡信息
     * @param
     * @return
     */
    Response modifyBank(BankAddDTO BankAddDTO);
    /**
     * 删除银行卡信息
     * @param
     * @return
     */
    Response deleteBank(BankAddDTO BankAddDTO);
    /**
     * 银行卡列表信息
     */
    Response  QueryBankList(BankAddDTO BankAddDTO);
}
