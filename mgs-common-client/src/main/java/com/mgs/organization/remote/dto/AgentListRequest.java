package com.mgs.organization.remote.dto;

import com.mgs.common.BaseRequest;
import lombok.Data;

/**
 * @author py
 * @date 2019/6/29 17:37
 **/
@Data
public class AgentListRequest extends BaseRequest {
    /**
     * 客户类型
     */
    private  Integer  agentType;
    /**
     * 客户名称
     */
    private  String agentName;
    /**
     * 客户编码
     */
    private  String agentCode;
    /**
     * 运营商编码
     */
    private  String companyCode;
    /**
     * 我司销售经理Id
     */
    private  Integer  saleManagerId;
    /**
     * 客户启用状态
     */
    private  Integer  availableStatus;
}
