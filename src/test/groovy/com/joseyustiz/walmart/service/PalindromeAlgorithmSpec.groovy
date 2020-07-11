package com.joseyustiz.walmart.service

import spock.lang.Specification
import spock.lang.Unroll

class PalindromeAlgorithmSpec extends Specification{
    def"a word with all the character different is not palindrome"(){
        given:
        def phrase = "123"
        expect:
        !PalindromeAlgorithm.isPalindrome(phrase)
    }

    def "a word with the same characters in the border are and the rest not, is not palindrome"(){
        given:
        def phrase = "1241"
        expect:
        !PalindromeAlgorithm.isPalindrome(phrase)
    }
    @Unroll
    def "a word with all the characters the same='#phrase' is palindrome "(){
        expect:
        PalindromeAlgorithm.isPalindrome(phrase)
        where:
        phrase << ["1111", "aaaaaaaaaaa"]
    }
    @Unroll
    def "isPalindrome is not case sensitive, e.g. '#phrase' is identified as palindrome"(){
        expect:
        PalindromeAlgorithm.isPalindrome(phrase)
        where:
        phrase << ["Anna", "Civic", "Kayak", "Level"]
    }
}
