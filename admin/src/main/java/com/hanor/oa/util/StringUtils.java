package com.hanor.oa.util;

public class StringUtils {

    /**
     * 字符串为空判断
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (null == str || str.length() == 0);
    }

    /**
     * 字符串非空判断
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str){
        return (null != str && str.length() > 0);
    }


}
