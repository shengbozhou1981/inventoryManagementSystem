package com.demo.service;

import com.demo.entity.OrderHead;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.vo.AddProductVo;
import com.demo.vo.OrderHeadFormVo;
import com.demo.vo.OrderHeadPageVo;
import com.demo.vo.OrderHeadVo;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-24
 */
public interface OrderHeadService extends IService<OrderHead> {

    public OrderHeadVo findByOrderId(String id);
    public String  insert(OrderHeadFormVo orderForm);

    public boolean addNewProduct(AddProductVo addProductForm);
    public OrderHeadPageVo listByPage(Long page, Long size);
    public List<OrderHeadVo> orderListWithDetail();
    public List<OrderHeadVo> recallCheck(String upc);
}
