package com.demo.service;

import com.demo.entity.DiscountProducts;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-28
 */
public interface DiscountProductsService extends IService<DiscountProducts> {

    public List<DiscountProducts> listDiscountProducts();
}
