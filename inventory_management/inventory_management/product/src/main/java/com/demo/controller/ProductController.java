package com.demo.controller;


import com.demo.entity.Product;
import com.demo.service.ProductService;
import com.demo.util.ResultVOUtil;
import com.demo.vo.ProductPageVo;
import com.demo.vo.ResultVo;
import com.sun.tracing.dtrace.ProviderAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-24
 */
@RestController
@RequestMapping("//product")
@Slf4j
public class ProductController {
    @Autowired
    ProductService productService;


    @GetMapping("/list")
    public ResultVo<List<Product>> productVoList() {

        List<Product> productsList = this.productService.list();
        if (!productsList.isEmpty()) {
            return ResultVOUtil.success(productsList);
        }
        return ResultVOUtil.fail();
    }

    // list result by page
    @GetMapping("/listByPage/{page}/{size}")
    public ResultVo<List<ProductPageVo>> productListByPage(@PathVariable("page") Long page,@PathVariable("size") Long size) {
        ProductPageVo productPageVo = this.productService.listByPage(page, size);
        if (productPageVo == null) {
            throw new RuntimeException();
        }
        return ResultVOUtil.success(productPageVo);
    }


    @GetMapping("/findByProductId/{id}")
    public Product findByProductId(@PathVariable("id") Integer id) {
        Product byProductId = this.productService.findByProductId(id);
        return byProductId;
    }

    @GetMapping("/findByUPC/{upc}")
    public Product findByUPC(@PathVariable("upc") String upc) {
        Product byUpc = this.productService.findByUPC(upc);
        System.out.println(byUpc);
        return byUpc;
    }

    @PutMapping("/update")
    public ResultVo update(@RequestBody Product product) {
        System.out.println(product);
        boolean update = productService.updateById(product);

        if (update) {
            return ResultVOUtil.success(product);
        } else {
            return ResultVOUtil.fail();
        }
    }

    @PutMapping("/subStock/{productId}/{quantity}")
    public ResultVo subStock(@PathVariable("productId") Integer productId, @PathVariable("quantity") Integer quantity) {

        Boolean subStock = this.productService.subStock(productId, quantity);
        System.out.println(subStock);
        if (subStock) {
            return ResultVOUtil.success("substock product Id "+ productId + ", success");
        }
        return ResultVOUtil.fail();
    }

    @PutMapping("/subStockByUpc/{upc}/{quantity}")
    public ResultVo subStockByUpc(@PathVariable("upc") String upc, @PathVariable("quantity") Integer quantity) {
        Boolean subStockByUpc = this.productService.subStockByUpc(upc, quantity);
        if (subStockByUpc) {
            return ResultVOUtil.success("substock UPC "+ upc + ", success");
        }
        return ResultVOUtil.fail();

    }

/*
    @PutMapping("/testDecreaseStock/{productId}/{quantity}")
    public ResultVo testDecreaseStock(@PathVariable("productId") Integer productId, @PathVariable("quantity") Integer quantity) {

        String decreaseStock = this.productService.decreaseStock(productId, quantity);
        System.out.println(decreaseStock);
        if (!"stock not enough ".equals(decreaseStock)||decreaseStock!=null) {
            return ResultVOUtil.success("substock product Id "+ productId + ", success");
        }
        return ResultVOUtil.fail();
    }
*/

    @GetMapping("/discountCheck")
    public ResultVo<Map<String,String>> discountCheck() {
        Map<String,String> discountProductMap = new HashMap<>();
        List<Product> productsList = this.productService.discountCheck();
        if (!productsList.isEmpty()) {
            for (Product product : productsList) {
                discountProductMap.put(product.getProductId().toString(),product.getUpc());
            }
        }
        if (!productsList.isEmpty()) {
            return ResultVOUtil.success(discountProductMap);
        }
        return ResultVOUtil.fail();
    }
}

