package com.demo.vo;

import com.demo.entity.OrderDetail;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Data
public class AddProductVo {
//    @NotEmpty(message = "name can't be null")
    private String customerName;
    //    @NotEmpty(message = "terminal Id can't be null")
    private Integer terminalId;
    private String orderId;
    private List<OrderDetail> items;
}
