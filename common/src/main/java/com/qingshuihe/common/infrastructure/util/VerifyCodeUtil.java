package com.qingshuihe.common.infrastructure.util;

import com.qingshuihe.common.utils.StringUtils;

import java.util.Random;

/**
 * @Description:
 * @Author: shl
 * @Date: 2023/1/5
 **/
public class VerifyCodeUtil {

    /**
     * @Description: 验证码生成范围,默认字符源
     * @Date: 2023/1/5
     **/
    private static final String VERIFY_SOURCES = "0123456879ABCDEFGHIJKLMNNOPQRSTUVWXYZ";
    /**
     * @Description: 验证码生成长度，默认长度
     * @Date: 2023/1/5
     **/
    private static final int VERIFY_SIZE = 4;

    /**
     * @Description: 使用系统默认长度和字符源生成验证码
     * @Date: 2023/1/5

     **/
    public static String generateVerifyCode(){
        return generateVerifyCode(VERIFY_SIZE,VERIFY_SOURCES);
    }
    /**
     * @Description: 使用系统默认字符源生成指定长度验证码
     * @Date: 2023/1/5

     **/
    public static String generateVerifyCode(int verifySize){
        return generateVerifyCode(verifySize,VERIFY_SOURCES);
    }

    /**
     * @Description: 使用指定字符源生成指定长度的验证码
     * @Date: 2023/1/5
     * @Param verifySize: 指定字符长度
     * @Param source: 指定字符源
     **/
    public static String generateVerifyCode(int verifySize,String sources){
        if (StringUtils.isEmpty(sources)){
            sources = VERIFY_SOURCES;
        }
        int soucreLength = sources.length();
        Random random = new Random(System.currentTimeMillis());
        StringBuilder stringBuilder = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            stringBuilder.append(sources.charAt(random.nextInt(soucreLength-1)));
        }
        return stringBuilder.toString();
    }
}
