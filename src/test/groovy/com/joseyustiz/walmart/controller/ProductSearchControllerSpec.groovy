package com.joseyustiz.walmart.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.joseyustiz.walmart.domain.Product
import com.joseyustiz.walmart.service.ProductSearchService
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
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
    private PageRequest defaultPaging = PageRequest.of(0, 20)

    void setup() {
        service = Stub(ProductSearchService)
        controller = new ProductSearchController(service)
    }

    def "when an exception is thrown it is leave to the Controller Avise"() {
        given:
        def message = "Exception Message"
        service.getProductsByPhrase(_, _) >> { throw new Exception(message) }
        when:
        controller.getProductsByPhrase(PALINDROME, defaultPaging)

        then:
        def e = thrown(Exception)
        e.getCause().getMessage() == message

    }

    def "when la list of product is return fron the service it is successfully return as json"() {
        given:
        service.getProductsByPhrase(phrase, defaultPaging) >> products

        when:
        def productsReturned = controller.getProductsByPhrase(phrase, defaultPaging)
        then:
        productsReturned == products
        where:
        phrase | products
        "aba"  | new PageImpl<>([[Product.builder().id(1).brand("brand aba").description("description")
                                          .imageUrl("http://walmart.com").price(2)
                                          .percentageOfDiscount(50).build()]])
        "1"    | new PageImpl<>([[Product.builder().id(1).brand("brand aba").description("description")
                                          .imageUrl("http://walmart.com").price(2)
                                          .percentageOfDiscount(0).build()]])
    }

}
