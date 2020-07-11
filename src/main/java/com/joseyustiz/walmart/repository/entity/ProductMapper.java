package com.joseyustiz.walmart.repository.entity;

import com.joseyustiz.walmart.domain.Product;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class ProductMapper {
    public Optional<Product> map(com.joseyustiz.walmart.repository.entity.Product product){
        if(product == null)
            return Optional.empty();
        return Optional.of(new Product(product.getId(), product.getDescription(),product.getBrand(),product.getImage(),(double)product.getPrice()));
    }
}
