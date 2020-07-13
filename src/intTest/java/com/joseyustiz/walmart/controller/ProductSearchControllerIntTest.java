package com.joseyustiz.walmart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joseyustiz.walmart.WalmartApplication;
import com.joseyustiz.walmart.domain.Product;
import com.joseyustiz.walmart.service.ProductSearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = WalmartApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductSearchControllerIntTest {
    public static final String PHRASE_REQUEST_PARAM = "phrase";
    private final String url = "/products";
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductSearchService service;

    private static Stream<Arguments> invalidPhrases() {
        return Stream.of(
                arguments("ab", 1),
                arguments("abç", 1),
                arguments("", 2),
                arguments(null, 1)
        );
    }

    private static Stream<Arguments> validProductList() {
        return Stream.of(
                arguments("aba", Collections.singletonList(Product.builder().id(1).brand("brand aba").description("description").imageUrl("http://walmart.com").price(2.0).percentageOfDiscount(50.0).build())),
                arguments("1", Collections.singletonList(Product.builder().id(1).brand("brand aba").description("description").imageUrl("http://walmart.com").price(2.0).percentageOfDiscount(0.0).build()))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPhrases")
    void searchInvalidPhrase_ReturnBadRequest(String phrase, int numberConstrainViolations) throws Exception {
        ResultActions response = this.mockMvc.perform(get(url)
                .param(PHRASE_REQUEST_PARAM, phrase));

        response.andExpect(status().isBadRequest());
        response.andExpect(jsonPath("$.errors", hasSize(numberConstrainViolations)));
    }

    @Test
    void whenAnExceptionIsThrown_ReturnInternalServerError() throws Exception {
        when(service.getProductsByPhrase(any(), any())).thenThrow(new RuntimeException());

        String PALINDROME = "aba";
        ResultActions response = this.mockMvc.perform(get(url)
                .param(PHRASE_REQUEST_PARAM, PALINDROME));

        response.andExpect(status().isInternalServerError());
        response.andExpect(content().string("{\"status\":\"INTERNAL_SERVER_ERROR\",\"errors\":[\"error occurred\"]}"));

    }

    @ParameterizedTest
    @MethodSource("validProductList")
    void whenListOfProductIsReturnFronTheService_ItIsSuccessfullyReturnAsJson(String phrase, List<Product> products) throws Exception {
        when(service.getProductsByPhrase(any(), any())).thenReturn(new PageImpl<>(products));
        ResultActions response = this.mockMvc.perform(get(url).param(PHRASE_REQUEST_PARAM, phrase).param("page", "1").param("size", "5"));

        response.andExpect(status().isOk());
        response.andExpect(content().string(objectMapper.writeValueAsString(new PageImpl<>(products))));

    }
}
