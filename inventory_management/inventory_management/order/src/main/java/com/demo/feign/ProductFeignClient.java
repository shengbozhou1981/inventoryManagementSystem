package com.demo.feign;

import com.demo.entity.DiscountProducts;
import com.demo.entity.Product;
import com.demo.vo.ResultVo;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@FeignClient("product-service")
//@Headers({ "terminalId: 101", "token: token101" })
//@FeignClient(value = "product-service", url = "https://")
//@FeignClient(value = "product-service",url = "https://product-service")
public interface ProductFeignClient {

    @GetMapping("product/findByProductId/{id}")
    public Product findByProductId(@PathVariable("id") Integer id,@RequestHeader(value = "terminalId") String terminalId,@RequestHeader(value = "token") String token );

    @PutMapping("product/subStock/{productId}/{quantity}")
    public ResultVo subStock(@PathVariable("productId") Integer productId, @PathVariable("quantity") Integer quantity,@RequestHeader(value = "terminalId") String terminalId,@RequestHeader(value = "token") String token );

    @PutMapping("product/subStockByUpc/{upc}/{quantity}")
    public ResultVo subStockByUpc(@PathVariable("upc") String upc, @PathVariable("quantity") Integer quantity,@RequestHeader(value = "terminalId") String terminalId,@RequestHeader(value = "token") String token );

    @GetMapping("product/findByUPC/{upc}")
    public Product findByUPC(@PathVariable("upc") String upc,@RequestHeader(value = "terminalId") String terminalId,@RequestHeader(value = "token") String token );

    @GetMapping("product/discountCheck")
    public ResultVo<Map<String, String>> discountCheck(@RequestHeader(value = "terminalId") String terminalId,@RequestHeader(value = "token") String token );

    @GetMapping(value = "discount/listDiscountProducts", produces = MediaType.APPLICATION_JSON_VALUE)
//
    public List<DiscountProducts> listDiscountProducts(@RequestHeader(value = "terminalId") String terminalId,@RequestHeader(value = "token") String token );

    @GetMapping("product/list")
    public List<DiscountProducts> list(@RequestHeader(value = "terminalId") String terminalId,@RequestHeader(value = "token") String token );

    @GetMapping("discount/listByProductId/{productId}")
    public DiscountProducts listByProductId(@PathVariable("productId") Integer productId,@RequestHeader(value = "terminalId") String terminalId,@RequestHeader(value = "token") String token );
}
