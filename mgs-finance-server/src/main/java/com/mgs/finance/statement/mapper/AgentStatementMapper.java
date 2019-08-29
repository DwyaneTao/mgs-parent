package com.mgs.finance.statement.mapper;

import com.mgs.common.MyMapper;
import com.mgs.finance.remote.statement.request.QueryAgentStatementListDTO;
import com.mgs.finance.remote.statement.request.QueryUncheckOutAgentListDTO;
import com.mgs.finance.remote.statement.response.AgentStatementListResponseDTO;
import com.mgs.finance.remote.statement.response.UncheckOutAgentDTO;
import com.mgs.finance.statement.domain.AgentStatementPO;

import java.util.List;

public interface AgentStatementMapper extends MyMapper<AgentStatementPO> {

    /**
     * 已出账单查询
     */
    List<AgentStatementListResponseDTO> queryStatementList(QueryAgentStatementListDTO request);

    /**
     * 未出账查询
     */
    List<UncheckOutAgentDTO> queryUncheckOutAgentList(QueryUncheckOutAgentListDTO request);
}