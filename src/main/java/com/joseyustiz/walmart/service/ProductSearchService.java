package com.joseyustiz.walmart.service;

import com.joseyustiz.walmart.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearchService {

    Page<Product> getProductsByPhrase(String phrase, Pageable pageable);
}