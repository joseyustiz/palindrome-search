package com.joseyustiz.walmart.service;

import com.joseyustiz.walmart.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductSearchDataAccess {

    List<Product> findByBrandOrDescription(String phrase);

    Optional<Product> findById(Integer id);
}