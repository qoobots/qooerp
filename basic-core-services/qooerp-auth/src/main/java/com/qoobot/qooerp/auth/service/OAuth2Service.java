package com.qoobot.qooerp.auth.service;

import com.qoobot.qooerp.auth.dto.TokenResponse;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Map;
import java.util.Set;

/**
 * OAuth2 业务服务
 */
public interface OAuth2Service {

    /**
     * 处理授权码授权
     */
    TokenResponse handleAuthorizationCodeGrant(
            String code, String redirectUri, RegisteredClient registeredClient);

    /**
     * 处理密码授权
     */
    TokenResponse handlePasswordGrant(
            String username, String password, RegisteredClient registeredClient);

    /**
     * 处理客户端凭证授权
     */
    TokenResponse handleClientCredentialsGrant(RegisteredClient registeredClient);

    /**
     * 处理刷新令牌授权
     */
    TokenResponse handleRefreshTokenGrant(
            String refreshToken, RegisteredClient registeredClient);

    /**
     * 存储授权码
     */
    void storeAuthorizationCode(
            String code,
            RegisteredClient registeredClient,
            Set<String> scopes,
            String redirectUri,
            String username);

    /**
     * 构建令牌响应 Map
     */
    Map<String, Object> buildResponseMap(TokenResponse response);

    /**
     * 从 refresh_token 获取用户名
     */
    String getUsernameFromRefreshToken(String refreshToken);

    /**
     * 将令牌加入黑名单
     */
    void addToBlacklist(String token, long expirySeconds);

    /**
     * 检查令牌是否在黑名单中
     */
    boolean isTokenBlacklisted(String token);
}
