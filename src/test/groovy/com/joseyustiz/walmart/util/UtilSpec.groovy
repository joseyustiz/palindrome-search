package com.joseyustiz.walmart.util

import spock.lang.Specification
import spock.lang.Unroll

class UtilSpec extends Specification {
    @Unroll
    def "null or blank string='#text' is not numeric"(){
        expect:
        !Util.isNumeric(text)
        where:
        text << [null, "", " "]
    }
    @Unroll
    def "word='#text'is not numeric"(){
        expect:
        !Util.isNumeric(text)
        where:
        text << ["hello", "1a"]
    }
    @Unroll
    def "number='#text' is identified as such"(){
        expect:
        Util.isNumeric(text)
        where:
        text << ["1", "2", "10"]
    }
}
