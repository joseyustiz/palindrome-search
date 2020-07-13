package com.joseyustiz.walmart.service;

import com.joseyustiz.walmart.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductSearchDataAccess {

    Page<Product> findByBrandOrDescription(String phrase, Pageable pageable);

    Optional<Product> findById(Integer id);
}