package com.joseyustiz.walmart.controller.dto

import spock.lang.Specification
import spock.lang.Unroll

class SearchPhraseValidatorSpec extends Specification {
    @Unroll
    def "invalid phase='#phrase' return false"() {
        given:
        def validator = new SearchPhraseValidator()
        expect:
        !validator.isValid(phrase, null)
        where:
        phrase << ["", "  ", " 1a", "ab", null]
    }
    @Unroll
    def "valid phase='#phrase' return true"() {
        given:
        def validator = new SearchPhraseValidator()
        expect:
        validator.isValid(phrase, null)
        where:
        phrase << ["1", "abc", " 1 1", "ab 1"]
    }
}
