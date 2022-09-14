package com.qingshuihe.common.utils;

import java.util.List;

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

        if (object instanceof List){
            if (((List<?>) object).size()==0){
                return true;
            }
        }
        return false;
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

}
