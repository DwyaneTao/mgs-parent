package com.mgs.fuzzyquery.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Owen
 * @Date: 2019/7/2 11:44
 * @Description:
 */
@Data
public class FuzzyAgentDTO implements Serializable {

    private String agentCode;

    private String agentName;
}
