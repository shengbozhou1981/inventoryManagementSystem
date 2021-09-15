package com.demo.exception;

import com.demo.enums.OrderEnum;

public class OrderException extends RuntimeException {

    public OrderException(OrderEnum orderEnum) {

        super(orderEnum.getMsg());
    }
}
