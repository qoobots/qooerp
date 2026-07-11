package com.qoobot.qooerp.auth.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 */
@Slf4j
@Component
public class JwtUtil {

    /**
     * JWT 密钥（从配置文件读取）
     */
    @Value("${jwt.secret:qooerp-secret-key-2026-please-change-in-production}")
    private String secretString;

    /**
     * Token 过期时间（从配置文件读取，默认7天）
     */
    @Value("${jwt.access-token-expiration:604800000}")
    private long expireTime;

    /**
     * JWT 签名密钥
     */
    private SecretKey secretKey;

    /**
     * 初始化密钥
     */
    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
        log.info("JWT密钥初始化完成");
    }

    /**
     * 生成 Token
     */
    public String generateToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(secretKey)
                .compact();
    }

    /**
     * 生成 Token
     */
    public String generateToken(Long userId, String username) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .claim("userId", userId)
                .claim("username", username)
                .subject(username)
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(secretKey)
                .compact();
    }

    /**
     * 解析 Token
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("解析Token失败: {}", e.getMessage());
            throw new RuntimeException("Token解析失败");
        }
    }

    /**
     * 验证 Token
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = parseToken(token);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取 Token 中的用户ID
     */
    public Long getUserId(String token) {
        Claims claims = parseToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * 获取 Token 中的用户名
     */
    public String getUsername(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取 Token 过期时间
     */
    public Date getExpiration(String token) {
        Claims claims = parseToken(token);
        return claims.getExpiration();
    }

    /**
     * 判断 Token 是否过期
     */
    public  boolean isExpired(String token) {
        try {
            Date expiration = getExpiration(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}
