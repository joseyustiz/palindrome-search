package com.joseyustiz.walmart.repository

import com.joseyustiz.walmart.repository.entity.Product
import com.joseyustiz.walmart.repository.entity.ProductMapper
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ProductDataAccessImplSpec extends Specification {
    private PageRequest defaultPaging = PageRequest.of(0, 20)

    def "search by id of a product that does not exist"() {
        given:
        def id = 1
        ProductRepository repo = Stub(ProductRepository)
        repo.findById(_ as Integer) >> Optional.empty()
        ProductMapper mapper = new ProductMapper()
        ProductDataAccessImpl da = new ProductDataAccessImpl(repo, mapper)
        when:
        def product = da.findById(id)
        then:
        !product.isPresent()
    }

    def "search by id of a product that does exist"(){
        given:
        ProductRepository repo = Stub(ProductRepository)
        def id = 11
        def brand = "brand aba"
        def description = "description"
        def url = "http://walmart.com"
        def price = 2
        repo.findById(_ as Integer) >> Optional.of(Product.builder().id(id).brand(brand).description(description)
                .image(url.toString()).price(price).build())
        ProductMapper mapper = new ProductMapper()
        ProductDataAccessImpl da = new ProductDataAccessImpl(repo, mapper)
        when:
        def product = da.findById(id)
        then:
        product.isPresent()
        with(product.get()){
            getId() == id
            getBrand() == brand
            getDescription() == description
            getImageUrl() == url
            getPrice() == price
        }
    }
    def "search by brand or description of a product that does not exist"(){
        given:
        def phrase = "xz"
        ProductRepository repo = Stub(ProductRepository)
        repo.findAllBy(_) >> new PageImpl<>([])
        ProductMapper mapper = new ProductMapper()
        ProductDataAccessImpl da = new ProductDataAccessImpl(repo, mapper)
        when:
        def products = da.findByBrandOrDescription(phrase, defaultPaging)
        then:
        products.getContent().isEmpty()
    }

    def "search by brand or description of a product that does exist"() {
        given:
        def phrase = "aba"
        ProductRepository repo = Stub(ProductRepository)

        def id = 11
        def brand = "brand aba"
        def description = "description"
        def url = "http://walmart.com"
        def price = 2
        repo.findAllBy(_, _) >> new PageImpl<>([Product.builder().id(id).brand(brand).description(description)
                                                        .image(url.toString()).price(price).build()])

        ProductMapper mapper = new ProductMapper()
        ProductDataAccessImpl da = new ProductDataAccessImpl(repo, mapper)
        when:
        def products = da.findByBrandOrDescription(phrase, defaultPaging)
        then:
        products.size() == 1
        with(products[0]) {
            getId() == id
            getBrand() == brand
            getDescription() == description
            getImageUrl() == url
            getPrice() == price
        }
    }
}
