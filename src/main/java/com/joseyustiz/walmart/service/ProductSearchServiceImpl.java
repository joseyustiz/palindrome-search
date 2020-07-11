package com.joseyustiz.walmart.service;

import com.joseyustiz.walmart.domain.Product;
import com.joseyustiz.walmart.util.Util;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService {
    private final ProductSearchGateway gateway;

    @Override
    public List<Product> getProductsByPhrase(@NonNull String phrase) {
        List<Product> products= null;
        if (Util.isNumeric(phrase))
            products = gateway.findById(Long.parseLong(phrase));

        if(products == null || products.size() == 0)
            products = gateway.findByBrandOrDescription(phrase);

        if(products.size() > 0) {
            Double percentageOfDiscount = calculatePercentageOfDiscount(phrase);
            products.forEach(p -> p.setPercentageOfDiscount(percentageOfDiscount));
        }
        return products;
    }

    private Double calculatePercentageOfDiscount(String phrase) {
        return PalindromeAlgorithm.isPalindrome(phrase) ? 50.0 : 0.0d;
    }
}
