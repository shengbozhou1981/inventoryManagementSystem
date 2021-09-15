package com.demo.service.impl;

import com.demo.entity.DiscountProducts;
import com.demo.entity.Product;
import com.demo.mapper.DiscountProductsMapper;
import com.demo.service.DiscountProductsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-28
 */
@Service
public class DiscountProductsServiceImpl extends ServiceImpl<DiscountProductsMapper, DiscountProducts> implements DiscountProductsService {
    @Autowired
    private DiscountProductsMapper discountProductsMapper;

    @Override
    public List<DiscountProducts> listDiscountProducts() {
        List<DiscountProducts> DiscountProductList = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat();//
        sdf.applyPattern("yyyy-MM-dd");//
        Date now = null;
        try {
            now = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<DiscountProducts> discountProductsList = this.discountProductsMapper.selectList(null);
        for (DiscountProducts product : discountProductsList) {
            Date fromDate = product.getFromDate();
            Date toDate = product.getToDate();
            if (fromDate != null && toDate != null) {
                if (now.after(fromDate) && now.before(toDate)) {
                    DiscountProductList.add(product);
                }
            }
        }
        return DiscountProductList;
    }
}
