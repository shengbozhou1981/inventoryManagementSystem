package com.demo.exception;


import com.demo.enums.ProductEnum;

public class ProductException extends RuntimeException {
    public ProductException(ProductEnum productEnum) {

        super(productEnum.getMsg());
    }
}
