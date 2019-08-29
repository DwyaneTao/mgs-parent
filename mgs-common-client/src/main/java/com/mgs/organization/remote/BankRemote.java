package com.mgs.organization.remote;

import com.mgs.common.Response;
import com.mgs.organization.remote.dto.BankAddDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author py
 * @date 2019/7/2 11:34
 **/
@FeignClient(value = "mgs-common-server")
public interface BankRemote {
    /**
     * 新增银行卡信息
     */
    @PostMapping("/bank/addBank")
    Response addBank(@RequestBody BankAddDTO request);
    /**
     * 修改银行卡信息
     */
    @PostMapping("/bank/modifyBank")
    Response modifyBank(@RequestBody BankAddDTO request);
    /**
     * 删除银行卡
     */
    @PostMapping("/bank/deleteBank")
    Response deleteBank(@RequestBody BankAddDTO request);

    /**
     * 银行卡列表
     */
    @PostMapping("/bank/queryBankList")
    Response queryBankList(@RequestBody BankAddDTO request);

}
