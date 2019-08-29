package com.mgs.organization.remote.dto;

import com.mgs.common.BaseRequest;
import lombok.Data;

/**
 * @author py
 * @date 2019/6/29 19:01
 **/
@Data
public class CompanyListRequest extends BaseRequest {
/**
 * 企业名称
 */
private  String  companyCode;
}
