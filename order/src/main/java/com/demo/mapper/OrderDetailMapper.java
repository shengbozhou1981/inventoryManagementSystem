package com.demo.mapper;

import com.demo.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.vo.SalesStatisticVo;

import java.util.List;

/**
 * <p>
 *  Mapper
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-24
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    public List<String> recallCheck(String upc);

    public List<SalesStatisticVo> salesReport(Integer days);
}
