package com.joseyustiz.walmart.repository;

import com.joseyustiz.walmart.domain.Product;
import com.joseyustiz.walmart.repository.entity.ProductMapper;
import com.joseyustiz.walmart.service.ProductSearchDataAccess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;

import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
public class ProductDataAccessImpl implements ProductSearchDataAccess {
    private final ProductRepository repo;
    private final ProductMapper mapper;

    @Override
    public Page<Product> findByBrandOrDescription(String phrase, Pageable pageable) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(phrase);
        Page<com.joseyustiz.walmart.repository.entity.Product> products = repo.findAllBy(criteria, pageable);
        return new PageImpl<>(products.get().map(p -> mapper.map(p).get()).collect(toList()));

    }

    @Override
    public Optional<Product> findById(Integer id) {
        Optional<com.joseyustiz.walmart.repository.entity.Product> products = repo.findById(id);
        if (products.isPresent())
            return mapper.map(products.get());

        return Optional.empty();
    }
}
