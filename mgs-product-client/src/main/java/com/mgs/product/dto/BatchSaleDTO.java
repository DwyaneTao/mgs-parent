package com.mgs.product.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * @Auther: Owen
 * @Date: 2019/6/19 01:35
 * @Description: 批量调整售价
 */
@Data
public class BatchSaleDTO extends BaseDTO {

    /**
     * 销售渠道（B2B,B2C,ctrip,qunar,feizhu,meituan,tcyl）
     */
    private String channelCode;

    /**
     * 运营商编码
     */
    private String companyCode;

    /**
     * 售售卖状态（1销售中 0仓库中）
     */
    private Integer saleStatus;

    /**
     * 产品售价列表
     */
    private List<BatchSaleItemDTO> itemList;
}
