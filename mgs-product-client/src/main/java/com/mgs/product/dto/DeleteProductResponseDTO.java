package com.mgs.product.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: Owen
 * @Date: 2019/6/29 19:36
 * @Description: 删除产品返回接口
 */
@Data
public class DeleteProductResponseDTO implements Serializable {

    /**
     * 删除状态（1成功删除 0不能删除）
     */
    private Integer status;

    /**
     * 不能删除原因
     */
    private String reason;
}
