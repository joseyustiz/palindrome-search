package com.joseyustiz.walmart.repository;

import com.joseyustiz.walmart.domain.Product;
import com.joseyustiz.walmart.repository.entity.ProductMapper;
import com.joseyustiz.walmart.service.ProductSearchDataAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.TextCriteria;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class ProductDataAccessImpl implements ProductSearchDataAccess {
    private final ProductRepository repo;
    private final ProductMapper mapper;
    @Override
    public List<Product> findByBrandOrDescription(String phrase) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(phrase.split(" "));
        List<com.joseyustiz.walmart.repository.entity.Product> products = repo.findAllBy(criteria);
        return products.stream().map(p -> mapper.map(p).get()).collect(toList());
    }

    @Override
    public Optional<Product> findById(Long id) {
        Optional<com.joseyustiz.walmart.repository.entity.Product> products = repo.findById(id);
        if(products.isPresent())
            return mapper.map(products.get());

        return Optional.empty();
    }
}
