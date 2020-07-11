package com.joseyustiz.walmart.service;

import com.joseyustiz.walmart.domain.Product;

import java.util.List;

public interface ProductSearchGateway {

    List<Product> findByIdOrBrandOrDescription(String phrase);
}