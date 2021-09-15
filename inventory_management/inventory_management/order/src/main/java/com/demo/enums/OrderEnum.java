package com.demo.enums;

import lombok.Getter;

@Getter
public enum OrderEnum {
    ORDER_PARAM_ERROR(0,"order parameters error"),
    ORDER_CREATE_FAIL(1,"creating order fail"),
    PRODUCT_NOT_EXIST(2,"product not exist"),
    ORDER_NOT_EXIST(3,"order not exist"),
    ORDER_STATUS_ERROR(4,"order status error"),
    PAY_STATUS_kERROR(5,"order payment error");

    OrderEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;
}
