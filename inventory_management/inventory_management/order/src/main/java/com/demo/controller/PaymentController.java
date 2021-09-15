package com.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.entity.OrderHead;
import com.demo.entity.Payment;
import com.demo.service.OrderHeadService;
import com.demo.service.PaymentService;
import com.demo.util.ResultVOUtil;
import com.demo.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-26
 */
@RestController
@RequestMapping("//payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private OrderHeadService orderHeadService;

    @GetMapping("/list")
    public ResponseEntity<ResultVo> listPayments() {
        List<Payment> paymentList = this.paymentService.list();
        if (!paymentList.isEmpty()) return ResponseEntity.ok(ResultVOUtil.success(paymentList));
        return ResponseEntity.ok(ResultVOUtil.fail());
    }

    @GetMapping("/listByOrderId/{orderId}")
    public ResponseEntity<ResultVo> listPaymentsByOrderId(@PathVariable("orderId") String orderId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", orderId);
        List<Payment> paymentList = this.paymentService.list(queryWrapper);
        if (!paymentList.isEmpty()) return ResponseEntity.ok(ResultVOUtil.success(paymentList));
        return ResponseEntity.ok(ResultVOUtil.fail());
    }

    @PutMapping("/paymentStatusUpdate/{orderId}/{status}")
    @Transactional
    public ResponseEntity<ResultVo> updateStatus(@PathVariable("orderId") String orderId, @PathVariable("status") String status) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", orderId);
        Payment payment = this.paymentService.getOne(queryWrapper);
        if (payment!=null) {
            payment.setPaymentStatus(status);
            boolean paymentUpdateResult = this.paymentService.updateById(payment);
            OrderHead orderHead = this.orderHeadService.getOne(queryWrapper);
            orderHead.setPayStatus(status);
            boolean orderHeadUpdateResult = this.orderHeadService.updateById(orderHead);
            if (paymentUpdateResult && orderHeadUpdateResult) {
                return ResponseEntity.ok(ResultVOUtil.success("payment status updates to " + status));
            }
        }
        return ResponseEntity.ok(ResultVOUtil.fail());
    }

}

