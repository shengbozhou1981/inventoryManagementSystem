package com.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.entity.OrderDetail;
import com.demo.entity.OrderHead;
import com.demo.enums.OrderEnum;
import com.demo.service.OrderDetailService;
import com.demo.service.OrderHeadService;
import com.demo.util.ResultVOUtil;
import com.demo.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-24
 */
@RestController
@RequestMapping("//orderHead")
@Slf4j
public class OrderHeadController {
    @Autowired
    OrderHeadService orderHeadService;
    @Autowired
    private OrderDetailService orderDetailService;

//List all orders
    @GetMapping("/list")
    public ResponseEntity<ResultVo> orderList() {
        List<OrderHead> ordersList = this.orderHeadService.list();
        if (!ordersList.isEmpty()) return ResponseEntity.ok(ResultVOUtil.success(ordersList));
        return ResponseEntity.ok(ResultVOUtil.fail());
    }

//    List all orders with order details and by page
    @GetMapping("/orderListByPage/{page}/{size}")
    public ResponseEntity<ResultVo> orderListByPage(@PathVariable("page") Long page, @PathVariable("size") Long size) {
        OrderHeadPageVo orderHeadPageVo = this.orderHeadService.listByPage(page, size);
        if (orderHeadPageVo == null) {
//            throw new RuntimeException();
            return ResponseEntity.ok(ResultVOUtil.fail());
        }
        return ResponseEntity.ok(ResultVOUtil.success(orderHeadPageVo));
    }

//    List all orders with order details
    @GetMapping("/orderListWithOrderDetail")
    public ResponseEntity<ResultVo> orderListWithDetail() {
        List<OrderHeadVo> orderHeadVoList = this.orderHeadService.orderListWithDetail();
        if (!orderHeadVoList.isEmpty()) return ResponseEntity.ok(ResultVOUtil.success(orderHeadVoList));
        return ResponseEntity.ok(ResultVOUtil.fail());
    }

//    Find an order by ID
    @GetMapping("/findByOrderId/{id}")
    public ResponseEntity<ResultVo> findByOrderId(@PathVariable("id") String id) {
        OrderHeadVo byOrderId = this.orderHeadService.findByOrderId(id);
        if (byOrderId != null) return ResponseEntity.ok(ResultVOUtil.success(byOrderId));
        return ResponseEntity.ok(ResultVOUtil.fail());
    }

//Create an order
    @PostMapping("/create")
    public ResponseEntity<ResultVo> create(@Valid @RequestBody OrderHeadFormVo orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("creat order, parameter error, orderForm = {}", orderForm);
//            resultVo.setData(bindingResult.getFieldError().getDefaultMessage());
//            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
//        save data into the orderHead table
        String orderId = this.orderHeadService.insert(orderForm);
        if (orderId == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResultVOUtil.fail());
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        return ResponseEntity.ok(ResultVOUtil.success(map));
    }

//    Add a product to an order by UPC and quantity
    @PutMapping("/addProductToOrder")
    public ResponseEntity<ResultVo> addProductToOrder(@Valid @RequestBody AddProductVo addProductForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("add product to existing order, parameter error, addProductForm = {}", addProductForm);
            return ResponseEntity.ok(ResultVOUtil.success(OrderEnum.ORDER_PARAM_ERROR));
//            resultVo.setData(bindingResult.getFieldError().getDefaultMessage());
//            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
//        save data into the orderHead table
        boolean insert = this.orderHeadService.addNewProduct(addProductForm);
        if (insert) return ResponseEntity.ok(ResultVOUtil.success("add product to order " + addProductForm.getOrderId() + ", success"));
        return ResponseEntity.ok(ResultVOUtil.fail());
    }

//    Close an order when payments are made in full
    @PutMapping("/updateOrderStatus/{orderId}")
    public ResponseEntity<ResultVo> updateStatus(
            @PathVariable("orderId") String orderId
    ) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", orderId);
        OrderHead orderHeadOne = orderHeadService.getOne(queryWrapper);
        if (orderHeadOne == null) {
            log.info("order not existing");
            return ResponseEntity.ok(ResultVOUtil.success(OrderEnum.ORDER_NOT_EXIST));
//            throw new RuntimeException("order not existing");
        }
        String payStatus = orderHeadOne.getPayStatus();
        if ("pending".equals(payStatus)) {
            log.info("not paid yet, can't close order");
            return ResponseEntity.ok(ResultVOUtil.success(OrderEnum.PAY_STATUS_kERROR));
        } else if ("paid".equals(payStatus)) {
            log.info("order already paid, now can set the order status to completed");
            orderHeadOne.setOrderStatus("completed");
            boolean updateResult = this.orderHeadService.updateById(orderHeadOne);
            if (updateResult) return ResponseEntity.ok(ResultVOUtil.success("payments are made in full, close order " + orderId + " successfully"));
        }
        return ResponseEntity.ok(ResultVOUtil.fail());
    }

    @GetMapping("/recallCheck/{upc}")
    public ResponseEntity<ResultVo> recallCheck(@PathVariable("upc") String upc) {
        List<OrderHeadVo> orderHeadVoList = this.orderHeadService.recallCheck(upc);
        if (!orderHeadVoList.isEmpty()) return ResponseEntity.ok(ResultVOUtil.success(orderHeadVoList));
        return ResponseEntity.ok(ResultVOUtil.fail());
    }

}

