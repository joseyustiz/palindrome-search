package com.joseyustiz.walmart.repository.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.net.URL;

@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Builder
@Document(language = "spanish", collection = "products")
public class Product {
    @Id
    private ObjectId _id;
    @Field("id")
    private Integer id;
    @TextIndexed
    private String description;
    @TextIndexed
    private String brand;
    private String image;
    private Integer price;

}
