package com.mgs.dlt.request.base;

import lombok.Data;

/**
 *   2018/4/8.
 */
@Data
public class PagingType {

    private Integer pageSize;
    private Integer pageIndex;
    private Integer totalPages;
    private Integer totalRecords;
}
