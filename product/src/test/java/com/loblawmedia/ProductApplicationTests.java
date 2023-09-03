package com.demo;

import com.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductApplicationTests {
    @Autowired
    private ProductService productService;
    @Test
    void contextLoads() {
        String s = this.productService.decreaseStock(106, 1);

    }
 @Test
    public void decreaseStock(){
            this.productService.decreaseStock(106, 10);
    }

}
