package com.joseyustiz.walmart.repository;

import com.joseyustiz.walmart.repository.entity.Product;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, ObjectId> {
    Page<Product> findAllBy(TextCriteria criteria, Pageable pageable);

    @Query("{ 'id' : ?0 }")
    Optional<Product> findById(Integer id);
}