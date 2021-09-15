package com.demo.enums;

import lombok.Getter;

@Getter
public enum PaymentEnum {
    CASH(0,"Pay By Cash"),
    CREDIT_CARD(1,"Pay By CreditCard"),
    DEBIT_CARD(2,"Pay By DebitCard"),
    GIFT_CARD(3,"Pay By GiftCard");

    PaymentEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;
}
