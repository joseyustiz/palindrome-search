package com.joseyustiz.walmart.domain

import spock.lang.Specification
import spock.lang.Unroll

import java.text.DecimalFormat

class ProductSpec extends Specification {

    public static final DecimalFormat df = new DecimalFormat("#.00")

    def "can create product without discount"() {
        given:
        def id = 1
        def description = "description"
        def brand = "brand"
        def url = "http://walmart.com"
        def price = 2
        when:
        def product = new Product(id, description, brand, url, price)
        then:
        product != null
        with(product) {
            getId() == id
            getDescription() == description
            getBrand() == brand
            getImageUrl() == url
            getPrice() == price
        }
    }

    @Unroll
    def "getPriceMinusDiscount is correct='#priceMinusDiscount' for price='#price' minus discount='#discount'"() {
        given:
        def id = 1
        def description = "description"
        def brand = "brand"
        def url = "http://walmart.com"
        def product = Product.builder().id(id).description(description).brand(brand).imageUrl(url).price(price).build()
        when:
        product.setPercentageOfDiscount(discount)
        then:
        df.format(product.getPriceMinusDiscount()) == df.format(priceMinusDiscount)

        where:
        price | discount | priceMinusDiscount
        2.0     | 50.0     | 1.0
        3.0     | 0.0      | 3.0
        3.0     | 30.0     | 2.1
        3.0     | 33.3     | 2.0

    }
}
