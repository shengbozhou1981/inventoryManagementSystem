package com.demo.vo;


import com.demo.entity.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class OrderHeadVo {
    private String orderId;

    private String customerName;

    private Integer terminalId;

    private BigDecimal orderAmount;

    private String orderStatus;

    private String paymentMethod;

    private String payStatus;

    private String createTime;

    private String updateTime;
    private List<OrderDetail> orderDetailList;
}
