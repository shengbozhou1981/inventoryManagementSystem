package com.demo.enums;

import lombok.Getter;

@Getter
public enum ProductEnum {
    PRODUCT_STOCK_ERROR(0,"product stock error,stock not enough"),
    PRODUCT_ID_NULL(1,"product Id not exist"),
    PRODUCT_NOT_EXIST(2,"product not exist");

    ProductEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;
}
