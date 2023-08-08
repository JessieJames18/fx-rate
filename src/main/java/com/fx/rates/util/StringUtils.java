package com.fx.rates.util;

import org.apache.logging.log4j.util.Strings;

public class StringUtils {

    /*
        returns false if valid
     */
    public static boolean isNullOrEmpty(String text) {
        if(!Strings.isBlank(text) || !Strings.isEmpty(text) || text != null) {
            return true;
        }
        return false;
    }
}
