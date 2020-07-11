package com.joseyustiz.walmart.util;

import org.apache.commons.lang3.StringUtils;

public class Util {
    public static boolean isNumeric(String phrase){
        boolean numeric=false;
        if (StringUtils.isBlank(phrase) || phrase.trim().length() == 0) {
            return numeric;
        }
        String value = phrase.trim();
        try {
            Long.parseLong(value);
            numeric= true;
        }catch (NumberFormatException e){
        }
        return numeric;
    }
}
