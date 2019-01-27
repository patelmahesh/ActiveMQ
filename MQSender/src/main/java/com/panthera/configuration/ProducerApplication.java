package com.panthera.configuration;

import com.panthera.model.Product;
import com.panthera.service.ProductService;
import com.panthera.util.BasicUtil;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class ProducerApplication {

    static final Logger LOG = LoggerFactory.getLogger(ProducerApplication.class);

    private static final AtomicInteger id = new AtomicInteger();

    public static void main(String[] args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(
                AppInitializer.class);

        ProductService productService = (ProductService) context.getBean("productService");

        Product product = getProduct();
        LOG.info("Application : sending order {}", product);
        productService.sendProduct(product);

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
        }

        ((AbstractApplicationContext) context).close();
    }

    private static Product getProduct() {
        Product p = new Product();
        p.setName("Product " + id.incrementAndGet());
        p.setProductId(BasicUtil.getUniqueId());
        p.setQuantity(2);
        return p;
    }
}
