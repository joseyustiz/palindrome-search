package com.joseyustiz.walmart.controller;

import com.joseyustiz.walmart.controller.dto.ValidSearchPhrase;
import com.joseyustiz.walmart.domain.Product;
import com.joseyustiz.walmart.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ProductSearchController {
    public static final String REGEX_VALID_CHARACTERS = "^[A-Za-záéíóúüñÁÉÍÓÚÜÑ0-9 ]*$";
    private final ProductSearchService service;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(produces = "application/json")
    public Page<Product> getProductsByPhrase(@RequestParam(name = "phrase") @ValidSearchPhrase @NotBlank(message = "must not be blank")
                                             @Pattern(regexp = REGEX_VALID_CHARACTERS, message = "contains character not allowed")
                                                     String phrase, Pageable pageable) {
        Page<Product> palindrome = service.getProductsByPhrase(phrase, pageable);
        return palindrome;
    }
}
