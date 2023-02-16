package com.bjs.hedge.util;

public class StringUtils {
    /**
     * 如果字符串为空，返回def
     *
     * @param str
     * @param def
     * @return
     */
    public static String getString(String str, String def) {
        if (org.springframework.util.StringUtils.isEmpty(str)) {
            return def;
        }
        return str;
    }
}
