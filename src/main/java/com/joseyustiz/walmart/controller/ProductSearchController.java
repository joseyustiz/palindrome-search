package com.joseyustiz.walmart.controller;

import com.joseyustiz.walmart.controller.dto.ValidSearchPhrase;
import com.joseyustiz.walmart.domain.Product;
import com.joseyustiz.walmart.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Validated
public class ProductSearchController {
    public static final String REGEX_VALID_CHARACTERS = "^[A-Za-záéíóúüñÁÉÍÓÚÜÑ0-9 ]*$";
    private final ProductSearchService service;

    //    @PostMapping(path = "/by-phrase", produces = "application/json")
//    public List<Product> getProductsByPhrase(@Valid @RequestBody SearchPhraseDto dto) {
//        List<Product> palindrome = service.getProductsByPhrase(dto.getPhrase());
//        return palindrome;
//    }
    @GetMapping(produces = "application/json")
    public List<Product> getProductsByPhrase(@RequestParam(name = "phrase") @ValidSearchPhrase @NotBlank(message = "must not be blank")
                                             @Pattern(regexp = REGEX_VALID_CHARACTERS, message = "contains character not allowed")
                                                     String phrase) {
        List<Product> palindrome = service.getProductsByPhrase(phrase);
        return palindrome;
    }
}
