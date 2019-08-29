package com.mgs.dlt.request.base;

import lombok.Data;

/**
 * @Auther: Owen
 * @Date: 2019/8/9 22:31
 * @Description:
 */
@Data
public class Pager {

    private Integer pageSize;
    private Integer pageIndex;
    private Integer totalRecords;
    private Integer totalPages;
    private boolean isReturnTotalCount = true;
}
