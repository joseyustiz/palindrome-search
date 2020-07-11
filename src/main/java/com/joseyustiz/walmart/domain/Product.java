package com.joseyustiz.walmart.domain;

import lombok.*;

import javax.validation.constraints.*;
import java.net.URL;

@Builder
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Product {
    @Min(value = 0)
    private final Long id;

    @NotBlank
    private final String description;

    @NotBlank
    private final String brand;

    @NotNull
    private final URL imageUrl;

    @NotNull
    @DecimalMin(value = "0")
    private final Double price;

    @Setter
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Digits(integer = 3, fraction = 2)
    private Double percentageOfDiscount;

    public Product(Long id, String description, String brand, URL imageUrl, Double price) {
        this(id,description,brand,imageUrl,price,0.0);
    }

    public Double getPriceMinusDiscount() {
        return price - price * percentageOfDiscount / 100;
    }
}
