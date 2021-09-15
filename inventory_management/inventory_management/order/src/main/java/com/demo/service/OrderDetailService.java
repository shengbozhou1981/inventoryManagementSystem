package com.demo.service;

import com.demo.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.vo.SalesStatisticVo;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-24
 */
public interface OrderDetailService extends IService<OrderDetail> {

    public List<SalesStatisticVo> salesReport(Integer days);
}
