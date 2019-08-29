package com.mgs.dlt.request.base;

import com.mgs.common.enums.DltInterfaceEnum;
import lombok.Data;

/**
 * 代理通请求认证头信息
 *   2018/4/8.
 */
@Data
public class DltRequestHeader {

    private String url;
    private DltInterfaceEnum interfaceEnum;
    private String timestamp;
    private String signature;
    private String interfacekey;
    private Integer supplierID;

}
