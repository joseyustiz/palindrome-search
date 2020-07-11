package com.joseyustiz.walmart.configuration;

import com.joseyustiz.walmart.repository.ProductDataAccessImpl;
import com.joseyustiz.walmart.repository.ProductRepository;
import com.joseyustiz.walmart.repository.entity.ProductMapper;
import com.joseyustiz.walmart.service.ProductSearchDataAccess;
import com.joseyustiz.walmart.service.ProductSearchService;
import com.joseyustiz.walmart.service.ProductSearchServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PalindromeDiscountConfig {

    @Bean
    public ProductSearchDataAccess productSearchDataAccess(ProductRepository repo){
        return new ProductDataAccessImpl(repo, new ProductMapper());
    }

    @Bean
    public ProductSearchService productSearchService(ProductSearchDataAccess gateway){
        return new ProductSearchServiceImpl(gateway);
    }

}
