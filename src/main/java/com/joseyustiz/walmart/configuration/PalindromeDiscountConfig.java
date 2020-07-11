package com.joseyustiz.walmart.configuration;

import com.joseyustiz.walmart.service.ProductSearchService;
import com.joseyustiz.walmart.service.ProductSearchServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PalindromeDiscountConfig {
    @Bean
    public ProductSearchService productSearchService(){
        return new ProductSearchServiceImpl();
    }

}
