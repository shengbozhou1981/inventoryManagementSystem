package com.demo.service;

import com.demo.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.vo.ProductPageVo;
import com.demo.vo.ResultVo;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-24
 */
public interface ProductService extends IService<Product> {
    public Product findByProductId(Integer id);
    public Product findByUPC(String upc);
    public ProductPageVo listByPage(Long page, Long size);

    public Boolean subStock(Integer productId, Integer quantity);

    public String decreaseStock(Integer upc, int i);

    public Boolean subStockByUpc(String upc, Integer quantity);

    public List<Product> discountCheck();
}
