package com.joseyustiz.walmart.configuration;

import com.joseyustiz.walmart.repository.ProductDataAccessImpl;
import com.joseyustiz.walmart.repository.ProductRepository;
import com.joseyustiz.walmart.repository.entity.Product;
import com.joseyustiz.walmart.repository.entity.ProductMapper;
import com.joseyustiz.walmart.service.ProductSearchDataAccess;
import com.joseyustiz.walmart.service.ProductSearchService;
import com.joseyustiz.walmart.service.ProductSearchServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;

@Configuration
public class PalindromeDiscountConfig {

    public void mongoTemplate(MongoTemplate template){
        TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("description")
                .onField("brand")
                .build();
        template.indexOps(Product.class).ensureIndex(textIndex);
    }

    @Bean
    public ProductSearchDataAccess productSearchDataAccess(ProductRepository repo, MongoTemplate template){
        mongoTemplate(template);
        return new ProductDataAccessImpl(repo, new ProductMapper());
    }

    @Bean
    public ProductSearchService productSearchService(ProductSearchDataAccess gateway){
        return new ProductSearchServiceImpl(gateway);
    }
}
