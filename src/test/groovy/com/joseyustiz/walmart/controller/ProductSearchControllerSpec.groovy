package com.joseyustiz.walmart.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.joseyustiz.walmart.controller.dto.SearchPhraseDto
import com.joseyustiz.walmart.controller.error.ErrorControllerAdvice
import com.joseyustiz.walmart.domain.Product
import com.joseyustiz.walmart.service.ProductSearchService
import org.springframework.http.MediaType
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class ProductSearchControllerSpec extends Specification {
    public static final String PALINDROME = "aba"
    private final def url = "/products/searches/by-phrase"
    private def objectMapper = objectMapper = new ObjectMapper()
    @Shared
    private def mockMvc
    @Shared
    private ProductSearchService service

    void setup() {
        service = Stub(ProductSearchService)
        mockMvc = standaloneSetup(new ProductSearchController(service)).setControllerAdvice(new ErrorControllerAdvice()).build()
    }
    @Unroll
    def "search invalid phrase='#phrase' return a Bad Request"() {
        given:
        def searchPhrase = new SearchPhraseDto(phrase)
        when:
        def response = mockMvc.perform(post(url).header("Content-Type", MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchPhrase))
        )
        then:
        response.andExpect(status().isBadRequest())
        response.andExpect(jsonPath('$.errors', hasSize(numberConstrainViolations)))

        where:
        phrase | numberConstrainViolations
        "ab"   | 1
        "abç"  | 1
        ""     | 2
        null   | 2


    }

    def "when an exception is thrown the endpoint return Internal Server Error"() {
        given:
        service.getProductsByPhrase(_) >> { throw new Exception() }
        def searchPhrase = new SearchPhraseDto(PALINDROME)
        when:
        def response = mockMvc.perform(post(url).header("Content-Type", MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchPhrase))
        )
        then:
        response.andExpect(status().isInternalServerError())
        response.andExpect(content().string("{\"status\":\"INTERNAL_SERVER_ERROR\",\"errors\":[\"error occurred\"]}"))

    }

    def "when la list of product is return fron the service it is successfully return as json"() {
        given:
        service.getProductsByPhrase(phrase) >> products
        def searchPhrase = new SearchPhraseDto(phrase)
        when:
        def response = mockMvc.perform(post(url).header("Content-Type", MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchPhrase))
        )
        then:
        response.andExpect(status().isOk())
        response.andExpect(content().string(objectMapper.writeValueAsString(products)))
        where:
        phrase | products
        "aba"  | [[Product.builder().id(1).brand("brand aba").description("description")
                           .imageUrl(new URL("http://walmart.com")).price(2)
                           .discount(50).priceMinusDiscount(1).build()]]
        "1"    | [[Product.builder().id(1).brand("brand aba").description("description")
                           .imageUrl(new URL("http://walmart.com")).price(2)
                           .discount(0).priceMinusDiscount(2).build()]]
    }

}
