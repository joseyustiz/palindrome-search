package com.joseyustiz.walmart.service;

import com.joseyustiz.walmart.domain.Product;
import com.joseyustiz.walmart.util.Util;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService {
    private final ProductSearchDataAccess gateway;

    @Override
    public Page<Product> getProductsByPhrase(@NonNull String phrase, Pageable pageable) {
        Page<Product> products = null;
        if (Util.isNumeric(phrase)) {
            Optional<Product> product = gateway.findById(Integer.parseInt(phrase));
            if (product.isPresent())
                products = new PageImpl<>(Collections.singletonList(product.get()));
        }
        if (products == null)
            products = gateway.findByBrandOrDescription(phrase, pageable);

        if (products.getContent().size() > 0) {
            Double percentageOfDiscount = calculatePercentageOfDiscount(phrase);
            products.forEach(p -> p.setPercentageOfDiscount(percentageOfDiscount));
        }
        return products;
    }

    private Double calculatePercentageOfDiscount(String phrase) {
        return PalindromeAlgorithm.isPalindrome(phrase) ? 50.0 : 0.0d;
    }
}
