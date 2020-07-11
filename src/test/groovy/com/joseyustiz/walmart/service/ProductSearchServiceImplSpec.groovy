package com.joseyustiz.walmart.service

import com.joseyustiz.walmart.domain.Product
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class ProductSearchServiceImplSpec extends Specification {
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
        given:
        gateway.findByIdOrBrandOrDescription(_) >> {throw new Exception("Exception")}
        when:
        service.getProductsByPhrase("aba")
        then:
        def e = thrown(Exception)
        e.getCause().message == "Exception"
    }

}
