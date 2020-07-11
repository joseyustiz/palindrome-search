package com.joseyustiz.walmart.service;

import com.joseyustiz.walmart.domain.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService{
    private final ProductSearchGateway gateway;
    @Override
    public List<Product> getProductsByPhrase(String phrase) {
        List<Product> products = gateway.findByIdOrBrandOrDescription(phrase);
        return products;
    }
}
