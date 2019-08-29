package com.mgs.finance.remote.lock.request;

import com.mgs.common.BaseRequest;
import lombok.Data;

import java.util.List;

@Data
public class QueryOrderFinanceLockListDTO extends BaseRequest{

    /**
     * 分销商编码
     */
    private String agentCode;

    /**
     * 分销商名称
     */
    private String agentName;

    /**
     * 锁定状态：0未锁定，1已锁定
     */
    private String lockStatus;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 商家编码
     */
    private String companyCode;
}
