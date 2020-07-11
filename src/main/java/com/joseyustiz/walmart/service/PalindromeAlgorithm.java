package com.joseyustiz.walmart.service;

public class PalindromeAlgorithm {
    public static boolean isPalindrome(String phrase) {
        boolean isPalindrome = true;
        String p = phrase.toLowerCase();
        for (int inicio = 0, fin = p.length() - 1; isPalindrome && inicio < fin; inicio++, fin--)
            if (p.charAt(inicio) != p.charAt(fin))
                isPalindrome = false;
        return isPalindrome;
    }
}
