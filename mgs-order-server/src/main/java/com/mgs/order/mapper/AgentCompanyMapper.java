package com.mgs.order.mapper;

import com.mgs.common.MyMapper;
import com.mgs.order.domain.AgentCompanyPO;

public interface AgentCompanyMapper  extends MyMapper<AgentCompanyPO> {

    AgentCompanyPO  queryAgentCompany(AgentCompanyPO agentCompanyPO);
}
