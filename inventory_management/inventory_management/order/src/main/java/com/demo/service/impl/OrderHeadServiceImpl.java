package com.demo.service.impl;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.entity.*;

import com.demo.enums.ProductEnum;
import com.demo.exception.OrderException;
import com.demo.exception.ProductException;
import com.demo.feign.ProductFeignClient;
import com.demo.mapper.OrderDetailMapper;
import com.demo.mapper.OrderHeadMapper;
import com.demo.mapper.ProductMapper;
import com.demo.service.OrderHeadService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.service.PaymentService;
import com.demo.vo.*;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.aspectj.weaver.ast.Or;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
public class OrderHeadServiceImpl extends ServiceImpl<OrderHeadMapper, OrderHead> implements OrderHeadService {
    @Autowired
    private OrderHeadMapper orderHeadMapper;
    @Autowired
    private OrderHeadService orderHeadService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${HST}")
    Double HST;

    public final static Map<String, String> cashierTerminalMap = new HashMap<String, String>() {{
        put("101", "token101");
        put("102", "token102");
        put("103", "token103");
        put("104", "token104");
        put("105", "token105");
        put("106", "token106");
    }};

    @Override
    public OrderHeadVo findByOrderId(String id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", id);
        OrderHead orderHead = this.orderHeadMapper.selectOne(queryWrapper);
        OrderHeadVo orderHeadVo = new OrderHeadVo();
        BeanUtils.copyProperties(orderHead, orderHeadVo);
//            check the order details which belong to this order
        List orderDetailList = this.orderDetailMapper.selectList(queryWrapper);
        orderHeadVo.setOrderDetailList(orderDetailList);

        return orderHeadVo;
    }

    @Override
//    @Transactional
    @GlobalTransactional
    public String insert(OrderHeadFormVo orderForm) {
//        check discount products
//        ResultVo<Map<String, String>> discountProductsMap = this.productFeignClient.discountCheck();
//        DiscountProducts[] discountList = this.restTemplate.getForObject("http://localhost:8281/discount/listDiscountProducts", DiscountProducts[].class);
//        List<DiscountProducts> discountList = this.restTemplate.getForObject("http://product-service/discount/listDiscountProducts", List.class);
        System.out.println(orderForm.getTerminalId().toString() + cashierTerminalMap.get(orderForm.getTerminalId().toString()));
        List<DiscountProducts> discountProductsList = this.productFeignClient.listDiscountProducts(orderForm.getTerminalId().toString(), cashierTerminalMap.get(orderForm.getTerminalId().toString()));
        List<String> productIdList = discountProductsList.stream().map((item) -> item.getProductId().toString()).collect(Collectors.toList());

//        change the orderForm from front end to OrderHeader and then save into database
        OrderHead orderHead = new OrderHead();
        orderHead.setCustomerName(orderForm.getCustomerName());
        orderHead.setTerminalId(orderForm.getTerminalId());
        orderHead.setOrderStatus("pending");
        orderHead.setPaymentMethod(orderForm.getPaymentMethod());
        orderHead.setPayStatus("pending");
// calculate order total amount
        List<OrderDetail> items = orderForm.getItems();
        BigDecimal totalAmount = new BigDecimal(0);
        Double discountFactor = 1.0;

        for (OrderDetail item : items) {

//judge whether the product is in the discount product list, if true, calculating the discount price
            DiscountProducts discountProduct = this.productFeignClient.listByProductId(item.getProductId(), orderForm.getTerminalId().toString(), cashierTerminalMap.get(orderForm.getTerminalId().toString()));
            if (discountProduct != null) {
                if (productIdList.contains(item.getProductId().toString()) && discountProduct.getWithProductId() == null && item.getAmount() >= discountProduct.getQuantityRequirement()) {
                    discountFactor -= discountProduct.getDiscountPercentage();
                } else if (productIdList.contains(item.getProductId().toString()) && discountProduct.getWithProductId() != null && productIdList.contains(discountProduct.getProductId())) {
                    discountFactor -= discountProduct.getDiscountPercentage();
                } else {
                    discountFactor = 1.0;
                }
            }


//  check whether stock is enough for the order, if true, go on next step, otherwise throw exception
            Integer amount = item.getAmount();
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("product_id", item.getProductId());
            Product product = productMapper.selectOne(queryWrapper);
            Integer stock = product.getInventory();
//            totalAmount = totalAmount.add(product.getPrice().multiply(new BigDecimal(item.getAmount())));
            if (stock >= amount) {
//  check the price of product and calculate the total price for this order
                totalAmount = totalAmount.add(product.getPrice().multiply(new BigDecimal(item.getAmount())).multiply(new BigDecimal(discountFactor)).multiply(new BigDecimal(1 + HST)));
            } else {
                log.info("product stock is not enough, product name is {}" + item.getProductName());
//                throw new RuntimeException("product stock is not enough, product name is {}" + item.getProductName());
                throw new ProductException(ProductEnum.PRODUCT_STOCK_ERROR);
            }
        }
        orderHead.setOrderAmount(totalAmount);

//   distributed lock for multi thread safe
        String key = "create_order_lock_" + orderForm.getCustomerName();
        RLock lock = redissonClient.getLock(key);
        lock.lock();
//        save the order data into orderhead table
        int insert = 0;
        try {
            insert = this.orderHeadMapper.insert(orderHead);

            if (insert == 1) {
//        save the order payment info into payment table
                Payment payment = new Payment();
                payment.setOrderId(orderHead.getOrderId());
                payment.setOrderAmount(totalAmount);
                payment.setPaymentMethod(orderForm.getPaymentMethod());
                payment.setPaymentStatus("pending");
                this.paymentService.save(payment);

//        save message to MQ

//                this.rocketMQTemplate.convertAndSend("myTopic","new order placed");

//        save the order details into orderDetail table
                for (OrderDetail item : items) {
                    //            get the product info by product id
                    Integer productId = item.getProductId();
                    Product productInfo = this.productFeignClient.findByProductId(productId, orderForm.getTerminalId().toString(), cashierTerminalMap.get(orderForm.getTerminalId().toString()));
                    System.out.println(productInfo);
                    BeanUtils.copyProperties(productInfo, item);
                    item.setPrice(productInfo.getPrice());
                    item.setOrderId(orderHead.getOrderId());
                    try {

                        this.orderDetailMapper.insert(item);
                    } catch (Exception e) {
                        log.info("order detail save fails, order id is {}", orderHead.getOrderId());
                        throw new RuntimeException("order detail save fails");
                    }
//                subtract Stock of  the product
//                    int i = 10/0;
                    this.restTemplate.getForObject("http://localhost:8083/cashier/insert/188/token188",String.class);
                    System.out.println("test distributed transaction");

                    try {
                        this.productFeignClient.subStock(productId, item.getAmount(), orderForm.getTerminalId().toString(), cashierTerminalMap.get(orderForm.getTerminalId().toString()));

                    } catch (Exception e) {
                        throw new RuntimeException(productInfo.getProductName() + " not enough stock");
                    }
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        if (insert == 1) {
            return orderHead.getOrderId();
        }
        return null;
    }

    @Override
//    @Transactional
    @GlobalTransactional
    public boolean addNewProduct(AddProductVo addProductForm) {
        System.out.println(addProductForm);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", addProductForm.getOrderId());
        OrderHead orderHead = orderHeadMapper.selectOne(queryWrapper);
        BigDecimal originalOrderAmount = orderHead.getOrderAmount();
        // calculate order total amount and then update the orderhead table
        List<OrderDetail> items = addProductForm.getItems();
        BigDecimal addAmount = new BigDecimal(0);
        for (OrderDetail item : items) {
//            check the price of product
            QueryWrapper wrapper = new QueryWrapper<>();
            wrapper.eq("upc", item.getUpc());
            Product product = productMapper.selectOne(wrapper);
//  check whether stock is enough for the order, if true, go on next step, otherwise throw exception
            Integer stock = product.getInventory();
            if (stock >= item.getAmount()) {
                addAmount = addAmount.add(product.getPrice().multiply(new BigDecimal(item.getAmount())).multiply(new BigDecimal(1 + HST)));
            } else {
                log.info("product stock is not enough, product name is {}" + item.getProductName());
                throw new RuntimeException("product stock is not enough, product name is {}" + item.getProductName());
            }
        }
        BigDecimal totalAmount = originalOrderAmount.add(addAmount);
        orderHead.setOrderAmount(totalAmount);

//   distributed lock for multi thread safe
        String key = "add_product_ToOrder_lock_" + addProductForm.getCustomerName();
        RLock lock = redissonClient.getLock(key);
        lock.lock();
        try {
//   adding the new added products price, and update order total price
            int updateResult = this.orderHeadMapper.updateById(orderHead);
//        save the order details into orderDetail table
            if (updateResult == 1) {
                for (OrderDetail item : items) {
                    //            get the product info by product id
                    String upc = item.getUpc();
                    Product productInfo = this.productFeignClient.findByUPC(upc, addProductForm.getTerminalId().toString(), cashierTerminalMap.get(addProductForm.getTerminalId().toString()));
                    BeanUtils.copyProperties(productInfo, item);

                    item.setOrderId(orderHead.getOrderId());
                    try {
                        this.orderDetailMapper.insert(item);
                    } catch (Exception e) {
                        log.info("order detail save fails, order id is {}", orderHead.getOrderId());
                        throw new RuntimeException("order detail save fails");
                    }
                    //                subStock the product
                    try {
                        System.out.println(upc);
                        System.out.println(item.getAmount());
                        this.productFeignClient.subStockByUpc(upc, item.getAmount(), addProductForm.getTerminalId().toString(), cashierTerminalMap.get(addProductForm.getTerminalId().toString()));
                    } catch (Exception e) {
                        throw new RuntimeException(productInfo.getProductName() + " not enough stock");
                    }
                }
                return true;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return false;
    }

    @Override
    public OrderHeadPageVo listByPage(Long page, Long size) {
        Page<OrderHead> orderHeadPage = new Page<>(page, size);
        QueryWrapper<OrderHead> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        Page<OrderHead> selectPage = this.orderHeadMapper.selectPage(orderHeadPage, queryWrapper);
        List<OrderHead> records = selectPage.getRecords();
        long total = selectPage.getTotal();
        long size1 = selectPage.getSize();
        OrderHeadPageVo orderHeadPageVo = new OrderHeadPageVo();
        orderHeadPageVo.setContent(records);
        orderHeadPageVo.setTotal(total);
        orderHeadPageVo.setSize(size1);

        return orderHeadPageVo;
    }

    @Override
    public List<OrderHeadVo> orderListWithDetail() {
        List<OrderHead> ordersList = this.orderHeadMapper.selectList(null);
        List<OrderHeadVo> orderHeadVoList = new ArrayList<>();
        for (OrderHead orderHead : ordersList) {
            OrderHeadVo orderHeadVo = new OrderHeadVo();
            BeanUtils.copyProperties(orderHead, orderHeadVo);
//            check the order details which belong to this order
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("order_id", orderHead.getOrderId());
            List orderDetailList = this.orderDetailMapper.selectList(queryWrapper);
            orderHeadVo.setOrderDetailList(orderDetailList);
            orderHeadVoList.add(orderHeadVo);
        }
        return orderHeadVoList;
    }

    @Override
    public List<OrderHeadVo> recallCheck(String upc) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("upc", upc);
        List<String> orderIdList = this.orderDetailMapper.recallCheck(upc);
        System.out.println(orderIdList);
        List<OrderHead> orderHeadList = this.orderHeadMapper.findOrderByIds(orderIdList);
        List<OrderHeadVo> orderHeadVoList = new ArrayList<>();
        for (OrderHead orderHead : orderHeadList) {
            OrderHeadVo orderHeadVo = new OrderHeadVo();
            BeanUtils.copyProperties(orderHead, orderHeadVo);
//            check the order details which belong to this order
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("order_id", orderHead.getOrderId());
            List orderDetailList = this.orderDetailMapper.selectList(wrapper);
            orderHeadVo.setOrderDetailList(orderDetailList);
            orderHeadVoList.add(orderHeadVo);
        }
        return orderHeadVoList;
    }
}
