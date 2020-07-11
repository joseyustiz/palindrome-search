package com.joseyustiz.walmart.controller;

import com.joseyustiz.walmart.controller.dto.SearchPhraseDto;
import com.joseyustiz.walmart.domain.Product;
import com.joseyustiz.walmart.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products/searches")
@RequiredArgsConstructor
public class ProductSearchController {
    private final ProductSearchService service;

    @PostMapping(path = "/by-phrase", produces = "application/json")
    public List<Product> getProductsByPhrase(@Valid @RequestBody SearchPhraseDto dto) {
        List<Product> palindrome = service.getProductsByPhrase(dto.getPhrase());
        return palindrome;
    }
}
