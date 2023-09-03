package com.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.entity.OrderDetail;
import com.demo.entity.SalesReport;
import com.demo.service.OrderDetailService;
import com.demo.service.SalesReportService;
import com.demo.util.ResultVOUtil;
import com.demo.vo.ResultVo;
import com.demo.vo.SalesStatisticVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-24
 */
@RestController
@RequestMapping("//orderDetail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private SalesReportService salesReportService;
    @GetMapping("/orderDetailByOrderId/{orderId}")
    public ResponseEntity<ResultVo> findOrderDetailByOrderId(
            @PathVariable("orderId") String orderId
    ){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        List orderDetailList = this.orderDetailService.list(queryWrapper);
        if (!orderDetailList.isEmpty()) return ResponseEntity.ok(ResultVOUtil.success(orderDetailList));
        return ResponseEntity.ok(ResultVOUtil.fail());
    }

    @GetMapping("/salesStatistic/{days}")
    public ResponseEntity<ResultVo> salesReport(@PathVariable("days") Integer days){
        List<SalesStatisticVo> salesStatisticVoList = this.orderDetailService.salesReport(days);
        if (!salesStatisticVoList.isEmpty()) return ResponseEntity.ok(ResultVOUtil.success(salesStatisticVoList));

        return ResponseEntity.ok(ResultVOUtil.fail());
    }

    @GetMapping("/salesStatistic")
    @Scheduled(0 0 12 * * ?)
    public ResponseEntity<ResultVo> salesReportDaily(){
        List<SalesStatisticVo> salesStatisticVoList = this.orderDetailService.salesReport(1);
        if (!salesStatisticVoList.isEmpty()) return ResponseEntity.ok(ResultVOUtil.success(salesStatisticVoList));

        return ResponseEntity.ok(ResultVOUtil.fail());
    }
}

