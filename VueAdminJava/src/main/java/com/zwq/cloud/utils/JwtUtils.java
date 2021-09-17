package com.zwq.cloud.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@Component
public class JwtUtils {

    private long expire=1800;

    private String secret="hjglaiybghjkhagglguNmafshfnglbgk";
    private String header="Authorization";

    /**
     * 生成
     *
     * @param username
     * @return
     */
    public String generateToken(String username) {
        // 当前时间
        Date nowDate = new Date();
        // 过期时间
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);
        // 加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                // 当前登录用户
                .setSubject(username)
                .setIssuedAt(nowDate)
                // 7天有效期
                .setExpiration(expireDate)
                .signWith(signatureAlgorithm, secret)
                .compact();


    }


    /**
     * 解析
     *
     * @param token
     * @return
     */
    public Claims getClaimsByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            // 防止别人篡改token 就会报错
            return null;
        }
    }

    /**
     * 获取jwt失效时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimsByToken(token).getExpiration();
    }

    /**
     *
     */
    public String getUsername(Claims claims) {
        return claims.getSubject();
    }

    /**
     * 是否过期的方法
     *
     * @param claims body主体
     * @return boolean
     */
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }


}
