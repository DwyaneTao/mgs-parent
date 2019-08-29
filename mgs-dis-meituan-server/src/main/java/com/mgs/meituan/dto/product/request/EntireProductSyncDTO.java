package com.mgs.meituan.dto.product.request;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 房态全量同步请求
 */
@Data
public class EntireProductSyncDTO {

    /**
     * 房态所属供应商
     */
    private String belongPartnerId;

    /**
     * 如果为正价，可为空；否则必填。
     */
    private String activeCode;

    /**
     * 是否使用参数rule中的销售规则：
     *  1：使用参数rule
     *  0：使用活动中的销售规则
     *  needChangeRules为0时，breakfastNum必须为0。
     *  默认值0
     */
    private String needChangeRules;

    /**
     * 产品详情
     */
    private JSONObject detail;


}
