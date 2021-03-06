package com.joseyustiz.walmart.repository

import com.joseyustiz.walmart.configuration.RepositoryConfig
import com.joseyustiz.walmart.repository.entity.Product
import com.joseyustiz.walmart.repository.entity.ProductMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

@DataMongoTest(excludeAutoConfiguration = RepositoryConfig.class)
class ProductDataAccessImplIntTest extends Specification{
    @Autowired ProductRepository repo
    private PageRequest defaultPaging = PageRequest.of(0, 20)

    void setup() {
        repo.deleteAll()
    }

    def "search by id of a product that does not exist"(){
        given:
        def id = 1
        ProductMapper mapper = new ProductMapper()
        ProductDataAccessImpl da = new ProductDataAccessImpl(repo, mapper)
        when:
        def product = da.findById(id)
        then:
        !product.isPresent()
    }

    def "search by id of a product that does exist"(){
        given:
        def id = 11
        def brand = "brand aba"
        def description = "description"
        def url = "http://walmart.com"
        def price = 2
        repo.save(Product.builder().id(id).brand(brand).description(description).image(url).price(price).build())
        repo.save(Product.builder().id(id+1).brand("brand").description("description").image(url).price(price).build())

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
        def phrase = "xyz"
        def id = 11
        def brand = "brand aba"
        def description = "description"
        def url = "http://walmart.com"
        def price = 2
        repo.save(Product.builder().id(id).brand(brand).description(description).image(url).price(price).build())
        repo.save(Product.builder().id(id+1).brand("brand").description("description").image(url).price(price).build())
        ProductMapper mapper = new ProductMapper()
        ProductDataAccessImpl da = new ProductDataAccessImpl(repo, mapper)
        when:
        def products = da.findByBrandOrDescription(phrase, defaultPaging)
        then:
        products.isEmpty()
    }

    def "search by brand or description of a product that does exist"(){
        given:
        def phrase = "aba"
        def id = 11
        def brand = "brand aba"
        def description = "description"
        def url = "http://walmart.com"
        def price = 2
        repo.save(Product.builder().id(id).brand(brand).description(description).image(url).price(price).build())
        repo.save(Product.builder().id(id+1).brand("brand").description("description").image(url).price(price).build())

        ProductMapper mapper = new ProductMapper()
        ProductDataAccessImpl da = new ProductDataAccessImpl(repo, mapper)
        when:
        def products = da.findByBrandOrDescription(phrase, defaultPaging)
        then:
        products.size() == 1
        with(products[0]){
            getId() == id
            getBrand() == brand
            getDescription() == description
            getImageUrl() == url
            getPrice() == price
        }
    }
}
