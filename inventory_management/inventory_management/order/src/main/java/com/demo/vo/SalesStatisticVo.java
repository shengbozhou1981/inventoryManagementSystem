package com.demo.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesStatisticVo {
    private String upc;
    private Integer productId;
    private String productName;
    private BigDecimal price;
    private Integer totalSales;

}
