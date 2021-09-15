package com.demo.mapper;

import com.demo.entity.OrderHead;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-24
 */
public interface OrderHeadMapper extends BaseMapper<OrderHead> {
    public List<OrderHead> findOrderByIds(List<String> orderIdList);

}
