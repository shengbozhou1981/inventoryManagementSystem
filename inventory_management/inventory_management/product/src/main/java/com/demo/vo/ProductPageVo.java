package com.demo.vo;

import com.demo.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductPageVo {
    private List<Product> content;
    private Long size;
    private Long total;
}
