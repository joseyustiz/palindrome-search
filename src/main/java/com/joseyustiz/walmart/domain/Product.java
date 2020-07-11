package com.joseyustiz.walmart.domain;

import lombok.Builder;
import lombok.Data;

import java.net.URL;

@Builder
@Data
public class Product {
    private Integer id;
    private String description;
    private String brand;
    private URL imageUrl;
    private Double price;
    private Integer discount;
    private Double priceMinusDiscount;
}
