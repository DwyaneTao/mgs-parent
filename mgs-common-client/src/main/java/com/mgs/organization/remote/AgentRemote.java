package com.mgs.organization.remote;

import com.mgs.common.PaginationSupportDTO;
import com.mgs.common.Response;
import com.mgs.organization.remote.dto.AgentAddDTO;
import com.mgs.organization.remote.dto.AgentCreditLineDTO;
import com.mgs.organization.remote.dto.AgentListRequest;
import com.mgs.organization.remote.dto.QueryAgentListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author py
 * @date 2019/7/1 21:12
 **/
@FeignClient(value = "mgs-common-server")
public interface AgentRemote {
/**
 * 添加客户信息
 */
   @PostMapping("/agent/addAgent")
   Response addAgent(AgentAddDTO agentAddDTO);
    /**
     * 修改客户启用状态
     */
    @PostMapping("/agent/modifyAgentStatus")
    Response modifyAgentStatus(@RequestBody AgentAddDTO request);
    /**
     * 修改客户信息
     */
    @PostMapping("/agent/modifyAgent")
    Response modifyAgent(@RequestBody AgentAddDTO request);
    /**
     * 客户详情
     */
    @PostMapping("/agent/queryAgentDetail")
    Response queryAgentDetail(@RequestBody AgentAddDTO request);
    /**
     * 查询客户列表
     */
    @PostMapping("/agent/queryAgentList")
    Response queryAgentList(AgentListRequest request);
    /**
    * 扣退额度
    */
    @PostMapping("/agent/modifyDeductRefundCreditLine")
    Response modifyDeductRefundCreditLine(@RequestBody List<AgentCreditLineDTO> agentCreditLineList);
}
