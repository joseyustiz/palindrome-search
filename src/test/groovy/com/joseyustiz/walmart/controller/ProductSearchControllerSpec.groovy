package com.joseyustiz.walmart.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.joseyustiz.walmart.controller.dto.SearchPhraseDto
import com.joseyustiz.walmart.domain.Product
import com.joseyustiz.walmart.service.ProductSearchService
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class ProductSearchControllerSpec extends Specification {
    public static final String PALINDROME = "aba"
    private final def url = "/products/searches/by-phrase"
    private def objectMapper = objectMapper = new ObjectMapper()
    @Shared
    private def mockMvc
    @Shared
    private ProductSearchService service
    @Shared
    @Subject
    private ProductSearchController controller

    void setup() {
        service = Stub(ProductSearchService)
        controller = new ProductSearchController(service)
    }

    def "when an exception is thrown it is leave to the Controller Avise"() {
        given:
        def message = "Exception Message"
        service.getProductsByPhrase(_) >> { throw new Exception(message) }
        when:
        controller.getProductsByPhrase(PALINDROME)

        then:
        def e = thrown(Exception)
        e.getCause().getMessage() == message

    }

    def "when la list of product is return fron the service it is successfully return as json"() {
        given:
        service.getProductsByPhrase(phrase) >> products
        def searchPhrase = new SearchPhraseDto(phrase)
        when:
        def productsReturned = controller.getProductsByPhrase(phrase)
        then:
        productsReturned == products
        where:
        phrase | products
        "aba"  | [[Product.builder().id(1).brand("brand aba").description("description")
                           .imageUrl("http://walmart.com").price(2)
                           .percentageOfDiscount(50).build()]]
        "1"    | [[Product.builder().id(1).brand("brand aba").description("description")
                           .imageUrl("http://walmart.com").price(2)
                           .percentageOfDiscount(0).build()]]
    }

}
