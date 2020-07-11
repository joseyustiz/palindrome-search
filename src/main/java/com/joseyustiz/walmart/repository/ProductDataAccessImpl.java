package com.joseyustiz.walmart.repository;

import com.joseyustiz.walmart.domain.Product;
import com.joseyustiz.walmart.repository.entity.ProductMapper;
import com.joseyustiz.walmart.service.ProductSearchDataAccess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
public class ProductDataAccessImpl implements ProductSearchDataAccess {
    private final ProductRepository repo;
    private final ProductMapper mapper;
    @Override
    public List<Product> findByBrandOrDescription(String phrase) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(phrase);
        List<com.joseyustiz.walmart.repository.entity.Product> products = repo.findAllBy(criteria);
        return products.stream().map(p -> mapper.map(p).get()).collect(toList());
    }

    @Override
    public Optional<Product> findById(Integer id) {
        Optional<com.joseyustiz.walmart.repository.entity.Product> products = repo.findById(id);
        if (products.isPresent())
            return mapper.map(products.get());

        return Optional.empty();
    }
}
