package com.joseyustiz.walmart.repository.entity;

import lombok.*;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.net.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(language = "spanish")
public class Product {

    @Field("id")
    private Long id;
    @TextIndexed
    private String description;
    @TextIndexed
    private String brand;
    private URL imageUrl;
    private Double price;

}
