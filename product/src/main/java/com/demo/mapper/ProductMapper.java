package com.demo.mapper;

import com.demo.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-24
 */
public interface ProductMapper extends BaseMapper<Product> {
    public Product findByProductId(Integer id);
}
