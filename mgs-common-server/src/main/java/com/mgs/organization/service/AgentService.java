package com.mgs.organization.service;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.organization.remote.dto.*;

/**
 * @author py
 * @date 2019/6/26 20:19
 **/
public interface AgentService {
    /**
     * 新增客户
     * @param
     * @return
     */
    Response addAgent(AgentAddDTO agentAddDTO);
    /**
     * 修改客户启用状态
     * @param
     * @return
     */
    Response modifyAgentStatus(AgentAddDTO agentAddDTO);
    /**
     * 修改客户信息
     * @param
     * @return
     */
    Response modifyAgent(AgentAddDTO agentAddDTO);
    /**
     * 根据客户Code,查询客户详情
     */
    Response queryAgentDetail(AgentAddDTO agentAddDTO);
    /**
     * 查询客户列表
     */
    PaginationSupportDTO<QueryAgentListDTO> queryAgentList(AgentListRequest request);
    /**
     * 扣退额度
     */
    Response deductRefundCreditLine(AgentCreditLineDTO agentAddDTO);
}
