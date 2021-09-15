package com.demo.service.impl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.entity.Product;
import com.demo.entity.StockAlert;
import com.demo.enums.ProductEnum;
import com.demo.exception.ProductException;
import com.demo.mapper.ProductMapper;
import com.demo.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.service.StockAlertService;
import com.demo.util.ResultVOUtil;
import com.demo.vo.ProductPageVo;
import com.demo.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Tony Zhou
 * @since 2021-08-24
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductService productService;
    @Autowired
    private StockAlertService stockAlertService;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public Product findByProductId(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id",id);
        Product product = this.productMapper.selectOne(queryWrapper);
        return product;
    }

    @Override
    public Product findByUPC(String upc) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("upc",upc);
        Product product = this.productMapper.selectOne(queryWrapper);
        return product;
    }

    @Override
    public ProductPageVo listByPage(Long page, Long size) {
//        create page
        Page<Product> productByPage = new Page<>(page,size);
        Page<Product> selectPage = this.productMapper.selectPage(productByPage, null);
        List<Product> productList = selectPage.getRecords();
        long total = selectPage.getTotal();
        long size1 = selectPage.getSize();
        ProductPageVo productPageVo = new ProductPageVo();
        productPageVo.setContent(productList);
        productPageVo.setTotal(total);
        productPageVo.setSize(size1);

        return productPageVo;
    }

    @Override
    @Transactional
    public Boolean subStock(Integer productId, Integer quantity) {
        String key = "dec_stock_lock_"+productId;
        RLock lock = redissonClient.getLock(key);
        lock.lock();
        Product productInfo = this.productService.findByProductId(productId);
        if (productInfo == null) {
            log.info("product not existing, productId = {}", productId);
            throw new ProductException(ProductEnum.PRODUCT_NOT_EXIST);
//            throw new RuntimeException("product not existing");
        } else {
            try {
                Integer inventory = productInfo.getInventory();
                Integer alertThreshold = productInfo.getStockAlertThreshold()==null? 0:productInfo.getStockAlertThreshold() ;

                int result = inventory - quantity;
                if (result < 0) {
                    log.info("product stock not enough, stock = {}", inventory);
                    throw new ProductException(ProductEnum.PRODUCT_STOCK_ERROR);
    //                throw new RuntimeException("product stock not enough");
                } else {
//  to do, add MQ to send the alert message,
                    if (result <= alertThreshold) {
                        log.info("product inventory lower than the alert Threshold");
                        StockAlert stockAlert = new StockAlert();
                        stockAlert.setAlertProduct(productInfo.getProductName());
                        stockAlert.setAlertProductUpc(productInfo.getUpc());
                        stockAlert.setAlertCurrentStock(result);
                        stockAlert.setAlertContent("product inventory lower than the alert Threshold, UPC is :" + productInfo.getUpc());
                        stockAlert.setCreateTime(LocalDateTime.now());
                        stockAlert.setUpdateTime(LocalDateTime.now());
                        this.stockAlertService.save(stockAlert);
                    }
                    productInfo.setInventory(result);
                    log.info(Thread.currentThread().getName() + ", current stock is :"+result);
                    int updateResult = this.productMapper.updateById(productInfo);
                    if (updateResult == 1) {
                        return true;
                    }
                    return false;
                }
            }
            catch (ProductException e) {
                e.printStackTrace();
                throw new ProductException(ProductEnum.PRODUCT_STOCK_ERROR);
            } finally {
                lock.unlock();
            }

        }
    }

    @Override
    @Transactional
    public Boolean subStockByUpc(String upc, Integer quantity) {
        String key = "dec_stock_byUpc_lock_"+upc;
        RLock lock = redissonClient.getLock(key);
        lock.lock();
        Product productInfo = this.productService.findByUPC(upc);
        if (productInfo == null) {
            log.info("product not existing, upc = {}", upc);
            throw new RuntimeException("product not existing");
        } else {
            try {
                Integer inventory = productInfo.getInventory();
                Integer alertThreshold = productInfo.getStockAlertThreshold()==null? 0:productInfo.getStockAlertThreshold() ;
                int result = inventory - quantity;
                if (result < 0) {
                    log.info("product stock not enough, stock = {}", inventory);
                    throw new RuntimeException("product stock not enough");
                } else {
                    if (result <= alertThreshold) {
                        log.info("product inventory lower than the alert Threshold");
                        StockAlert stockAlert = new StockAlert();

                        stockAlert.setCreateTime(LocalDateTime.now());
                        stockAlert.setUpdateTime(LocalDateTime.now());
                        stockAlert.setAlertProduct(productInfo.getProductName());
                        stockAlert.setAlertProductUpc(productInfo.getUpc());
                        stockAlert.setAlertCurrentStock(result);
                        stockAlert.setAlertContent("product inventory lower than the alert Threshold, UPC is :" + productInfo.getUpc());
                        this.stockAlertService.save(stockAlert);

                    }
                    productInfo.setInventory(result);
                    int updateResult = this.productMapper.updateById(productInfo);
                    if (updateResult==1) {
                        return true;
                    }

                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return false;
        }
    }

    @Override
    public List<Product> discountCheck() {
        List<Product> productList = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat();//
        sdf.applyPattern("yyyy-MM-dd");//
        Date now = null;
        try {
            now = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Product> list = this.productService.list();
        for (Product product : list) {
            Date fromDate = product.getFromDate();
            Date toDate = product.getToDate();
            if (fromDate != null&&toDate!=null) {
                if (now.after(fromDate) && now.before(toDate)){
                    productList.add(product);
                }
            }
        }

//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.isNotNull("from_date");
//        queryWrapper.between(sdf.format(date),"from_date","to_date");
//        List productList = this.productService.list(queryWrapper);


        return productList;
    }

    @Override
    @Transactional
    public String decreaseStock(Integer upc, int i) {
        String key = "dec_stock_lock_"+upc;
        RLock lock = redissonClient.getLock(key);
        lock.lock();
        try {
            Product byProductId = this.productService.findByProductId(Integer.valueOf(upc));
            Integer inventory = byProductId.getInventory();
            if (inventory >=i) {
                this.productService.subStock(upc,i);
                return "inventory is " + (inventory-i);
            }else{
                log.info("stock not enough");
                return "stock not enough ";
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }


}
