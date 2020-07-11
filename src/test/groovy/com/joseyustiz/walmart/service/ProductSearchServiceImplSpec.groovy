package com.joseyustiz.walmart.service

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
        service = new ProductSearchServiceImpl()
    }
}
