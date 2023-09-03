package com.demo.vo;

import com.demo.entity.OrderDetail;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Data
public class OrderHeadFormVo {
    @NotEmpty(message = "name can't be null")
    private String customerName;
//    @NotEmpty(message = "terminal Id can't be null")
    private Integer terminalId;
//    @NotEmpty(message = "order total amount can't be null")
    private Double orderAmount;
//    @NotEmpty(message = "")
    private String orderStatus;
    @NotEmpty(message = "payment method can't be null")
    private String paymentMethod;
    private String payStatus;
    @NotEmpty(message = "order detail can't be null")
    private List<OrderDetail> items;
}
