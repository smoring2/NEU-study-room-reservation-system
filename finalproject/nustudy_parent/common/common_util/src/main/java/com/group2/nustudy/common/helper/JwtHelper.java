package com.group2.nustudy.common.helper;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

public class JwtHelper {

    /**
     * expiration time
     */
    private static long tokenExpiration = 24*60*60*1000;
    /**
     * sign key
     */
    private static String tokenSignKey = "123456";

    /**
     * HMAC-SHA512 key derived from the sign key
     */
    private static SecretKey getSignKey() {
        byte[] keyBytes = Arrays.copyOf(tokenSignKey.getBytes(StandardCharsets.UTF_8), 64);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * generate token by parameters
     */
    public static String createToken(Long userId, String userName) {
        String token = Jwts.builder()
                .subject("YYGH-USER")
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith(getSignKey(), Jwts.SIG.HS512)
                .compressWith(Jwts.ZIP.GZIP)
                .compact();
        return token;
    }


    /**
     * Get user id by token
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        if(ObjectUtils.isEmpty(token)) return null;

        Jws<Claims> claimsJws = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
        Claims claims = claimsJws.getPayload();
        Integer userId = (Integer)claims.get("userId");
        return userId.longValue();
    }


    /**
     * Get user name by token
     * @param token
     * @return
     */
    public static String getUserName(String token) {
        if(ObjectUtils.isEmpty(token)) return "";

        Jws<Claims> claimsJws = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token);
        Claims claims = claimsJws.getPayload();
        return (String)claims.get("userName");
    }

    /**
     * Main
     * @param args
     */
    public static void main(String[] args) {
        String token = JwtHelper.createToken(1L, "lucy");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUserName(token));
    }
}

