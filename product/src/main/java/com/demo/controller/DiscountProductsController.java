package com.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.entity.DiscountProducts;
import com.demo.service.DiscountProductsService;
import com.demo.util.ResultVOUtil;
import com.demo.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-28
 */
@RestController
@RequestMapping("//discount")
public class DiscountProductsController {

    @Autowired
    private DiscountProductsService discountProductsService;


    @GetMapping("/list")
    public List<DiscountProducts> list() {
        return this.discountProductsService.list();
    }

    @GetMapping("/listDiscountProducts")
    public List<DiscountProducts> listDiscountProducts() {
        return  this.discountProductsService.listDiscountProducts();
    }

    @GetMapping("/listByProductId/{productId}")
    public DiscountProducts listByProductId(@PathVariable("productId") Integer productId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("active", 1);
        queryWrapper.eq("product_id", productId);
        return this.discountProductsService.getOne(queryWrapper);
    }

}

