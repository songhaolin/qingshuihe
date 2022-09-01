package com.qingshuihe.common.utils;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/1
 **/
public class StringUtils {

    public static boolean isEmpty(Object object) {
        if (null == object) {
            return true;
        }
        if (object.toString().isEmpty() || object.toString().trim().isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }
}
