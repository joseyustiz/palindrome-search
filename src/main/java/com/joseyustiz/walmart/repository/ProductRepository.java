package com.joseyustiz.walmart.repository;

import com.joseyustiz.walmart.repository.entity.Product;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {
    List<Product> findAllBy(TextCriteria criteria);
}