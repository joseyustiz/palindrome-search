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
    def"when an exception in the repository is thrown it is not caught"(){
        def exceptionMessage = "Exception"
        given:
        gateway.findByIdOrBrandOrDescription(_ as String) >> {throw new Exception(exceptionMessage)}
        when:
        service.getProductsByPhrase(PALINDROME)
        then:
        def e = thrown(Exception)
        e.getCause().message == exceptionMessage
    }

}
