package com.joseyustiz.walmart.repository;

import com.joseyustiz.walmart.repository.entity.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, ObjectId> {
    List<Product> findAllBy(TextCriteria criteria);
//    List<Product> findAllBy(org.springframework.data.mongodb.core.query.Query criteria);
    @Query("{ 'id' : ?0 }")
    Optional<Product> findById(Integer id);
}