package com.demo.vo;

import com.demo.entity.OrderHead;
import lombok.Data;

import java.util.List;

@Data
public class OrderHeadPageVo {
    private List<OrderHead> content;
    private Long total;
    private Long size;
}
