package com.joseyustiz.walmart.service

import com.joseyustiz.walmart.domain.Product
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class ProductSearchServiceImplSpec extends Specification {
    private static final String PALINDROME = "aba"
    @Shared
    private ProductSearchDataAccess gateway
    @Shared
    @Subject
    private ProductSearchService service
    private PageRequest defaultPaging = PageRequest.of(0, 20)

    void setup() {
        gateway = Stub(ProductSearchDataAccess)
        service = new ProductSearchServiceImpl(gateway)
    }

    def "when an exception in the repository is thrown it is not caught"() {
        given:
        def exceptionMessage = "Exception"
        gateway.findByBrandOrDescription(_ as String, _) >> { throw new Exception(exceptionMessage) }
        when:
        service.getProductsByPhrase(PALINDROME, defaultPaging)
        then:
        def e = thrown(Exception)
        e.getCause().message == exceptionMessage
    }

    @Unroll
    def "no palindrome word='#phrase' has not discount"() {
        given:
        gateway.findById()
        gateway.findByBrandOrDescription(phrase, _) >> listOfProducts
        when:
        def products = service.getProductsByPhrase(phrase, defaultPaging)
        then:
        products.size() == listOfProducts.size()
        products.every({ p -> p.getPrice() == p.getPriceMinusDiscount() && p.getPercentageOfDiscount() == 0d })
        where:
        phrase        | listOfProducts
        "brand"       | new PageImpl<>([Product.builder().id(1).brand("brand aba").description("description")
                                                .imageUrl("http://walmart.com").price(2).build()])
        "description" | new PageImpl<>([Product.builder().id(1).brand("brand aba").description("description")
                                                .imageUrl("http://walmart.com").price(2).build()])
    }

    @Unroll
    def "no palindrome id='#phrase' has not discount"() {
        given:
        gateway.findById(Long.parseLong(phrase)) >> listOfProducts
        gateway.findByBrandOrDescription(_, _) >> []
        when:
        def products = service.getProductsByPhrase(phrase, defaultPaging)
        then:
        products.size() == listOfProducts.size()
        products.every({ p -> p.getPrice() == p.getPriceMinusDiscount() && p.getPercentageOfDiscount() == 0d })
        where:
        phrase | listOfProducts
        "1"    | [Product.builder().id(1).brand("brand aba").description("description")
                          .imageUrl("http://walmart.com").price(2).build()]
    }

    @Unroll
    def "palindrome word='#phrase' does have discount"() {
        given:
        gateway.findByBrandOrDescription(phrase, _) >> listOfProducts
        when:
        def products = service.getProductsByPhrase(phrase, defaultPaging)
        then:
        products.size() == listOfProducts.size()
        products.every({ p -> p.getPrice() / 2 == p.getPriceMinusDiscount() && p.getPercentageOfDiscount() == 50.0d })
        where:
        phrase | listOfProducts
        "aba"  | new PageImpl<>([Product.builder().id(11).brand("brand aba").description("description")
                                         .imageUrl("http://walmart.com").price(2).build()])
        "11"   | new PageImpl<>([Product.builder().id(11).brand("brand aba").description("description")
                                         .imageUrl("http://walmart.com").price(2).build()])
    }

    @Unroll
    def "palindrome id='#phrase' does have discount"() {
        given:
        gateway.findById(Integer.valueOf(phrase)) >> listOfProducts
        when:
        def products = service.getProductsByPhrase(phrase, defaultPaging)
        then:
        products.size() == listOfProducts.size()
        products.every({ p -> p.getPrice() / 2 == p.getPriceMinusDiscount() && p.getPercentageOfDiscount() == 50.0d })
        where:
        phrase | listOfProducts
        "11"   | [Product.builder().id(11).brand("brand aba").description("description")
                          .imageUrl("http://walmart.com").price(2).build()]
    }

    @Unroll
    def "palindrome number='#phrase' is not id but it is on the brand or description, so it does have discount"() {
        given:
        gateway.findById(_) >> []
        gateway.findByBrandOrDescription(phrase, _) >> listOfProducts
        when:
        def products = service.getProductsByPhrase(phrase, defaultPaging)
        then:
        products.size() == listOfProducts.size()
        products.every({ p -> p.getPrice() / 2 == p.getPriceMinusDiscount() && p.getPercentageOfDiscount() == 50.0d })
        where:
        phrase | listOfProducts
        "11"   | new PageImpl<>([Product.builder().id(22).brand("brand aba 11").description("description").imageUrl("http://walmart.com").price(2).build(),
                                 Product.builder().id(222).brand("brand aba 111").description("description").imageUrl("http://walmart.com").price(2).build()])
        "33"   | new PageImpl<>([Product.builder().id(22).brand("brand aba").description("description 33")
                                         .imageUrl("http://walmart.com").price(2).build()])
    }
}
