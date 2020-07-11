package com.joseyustiz.walmart.service

import spock.lang.Specification

class ProductSearchServiceImplSpec extends Specification {
    private ProductSearchService service

    void setup() {
        service = new ProductSearchServiceImpl()
    }
}
