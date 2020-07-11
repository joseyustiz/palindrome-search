package com.joseyustiz.walmart.service

import com.joseyustiz.walmart.domain.Product
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class ProductSearchServiceImplSpec extends Specification {
    private static final String PALINDROME = "aba"
    @Shared
    private ProductSearchGateway gateway
    @Shared
    @Subject
    private ProductSearchService service

    void setup() {
        gateway = Stub(ProductSearchGateway)
        service = new ProductSearchServiceImpl(gateway)
    }

    def "when an exception in the repository is thrown it is not caught"() {
        given:
        def exceptionMessage = "Exception"
        gateway.findByIdOrBrandOrDescription(_ as String) >> { throw new Exception(exceptionMessage) }
        when:
        service.getProductsByPhrase(PALINDROME)
        then:
        def e = thrown(Exception)
        e.getCause().message == exceptionMessage
    }
    //no palindrome
    def "no palindrome words has not discount"() {
        given:
        gateway.findByIdOrBrandOrDescription("1") >> [Product.builder().id(1).brand("brand aba").description("description")
                                                              .imageUrl(new URL("http://walmart.com")).price(2)
                                                              .percentageOfDiscount(0).build()]
        when:
        def products = service.getProductsByPhrase("1")
        then:
        products.every({ p -> p.getPrice() == p.getPriceMinusDiscount() && p.getPercentageOfDiscount() == 0d })

    }
    //palindrome id, brand, description

}
