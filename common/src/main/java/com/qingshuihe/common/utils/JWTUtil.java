package com.qingshuihe.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @Description:jwt加解密工具类，主要用来生成token，并处理与token有关的问题
 * @Author: shl
 * @Date: 2022/8/31
 **/
public class JWTUtil {

    //有效时间
    private static final long JWT_TTL = 60 * 60 * 1000;
    //系统签名
    private static final String JWT_SIGN = "qingshuihe";


    public static String createJWT(String subject) {
        //加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //当前时间
        long nowMillis = System.currentTimeMillis();
        Date nowDate = new Date(nowMillis);

        //过期时间
        long expMills = nowMillis + JWT_TTL;
        Date expDate = new Date(expMills);

        String uuid = UUID.randomUUID().toString().replace("-", "");
        JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("type", "JWT")
                //设置唯一标识
                .setId(uuid)
                //设置当前时间
                .setIssuedAt(nowDate)
                //设置主题内容，即根据什么生成token，一般用username
                .setSubject(subject)
                //设置过期时间
                .setExpiration(expDate)
                //设置系统签名
                .setAudience(JWT_SIGN)
                //设置不能提前的开始时间点
                .setNotBefore(nowDate)
                //设置加密key,即加上系统签名做盐，双重加密所以比较安全
                .signWith(signatureAlgorithm, getSecretKey());
        return jwtBuilder.compact();

    }

    /**
     * 对token进行解析
     *
     * @param token
     * @return
     */
    public static Claims parseJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * 获取加密key
     *
     * @return
     */
    private static SecretKey getSecretKey() {
        byte[] encodeKey = Base64.getDecoder().decode(JWT_SIGN);
        SecretKey key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        return key;
    }
}
