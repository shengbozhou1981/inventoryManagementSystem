package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entity.Product;

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
