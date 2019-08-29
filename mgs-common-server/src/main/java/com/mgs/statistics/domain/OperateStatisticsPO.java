package com.mgs.statistics.domain;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class OperateStatisticsPO {

    private  Integer orderQty;

    private  Integer orderDay;

    private  Integer saleNightQty;

    private  String period;

    private BigDecimal sales;

    private  BigDecimal costs;

    private  String profit;



}
